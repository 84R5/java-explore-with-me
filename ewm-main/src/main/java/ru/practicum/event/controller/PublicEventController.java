package ru.practicum.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.enums.EventSort;
import ru.practicum.event.model.SearchFilter;
import ru.practicum.event.service.EventService;
import ru.practicum.model.StatsClient;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublicEventController {

    EventService eventService;
    StatsClient statsClient;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEvents(
            @RequestParam(value = "text", required = false) @Size(min = 1, max = 7000) String text,
            @RequestParam(value = "categories", required = false) List<Long> categoryIds,
            @RequestParam(value = "paid", required = false) Boolean paid,
            @RequestParam(value = "rangeStart", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(value = "rangeEnd", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(value = "sort",required = false, defaultValue = "EVENT_DATE") EventSort sort,
            @RequestParam(value = "from",required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size",required = false, defaultValue = "10") @Positive Integer size,
            HttpServletRequest request) {

        if (rangeEnd != null && rangeStart != null && rangeEnd.isBefore(rangeStart)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rangeEnd cannot be earlier than the rangeStart");
        }

        SearchFilter filter = SearchFilter.builder()
                .categoryIds(categoryIds)
                .rangeStart(rangeStart != null ?
                        rangeStart : LocalDateTime.now())
                .rangeEnd(rangeEnd)
                .text(text)
                .paid(paid)
                .onlyAvailable(onlyAvailable)
                .build();
        log.debug("GET adminFindAllByFilter() with param: {} ", filter);
        request.setAttribute("app_name", "main application");

        sendToStats(request);
        List<EventShortDto> eventShortDtoList = eventService.searchUsingFilterAndSorting(filter, sort, from, size);
        request.setAttribute("app_name", "main application");

        return ResponseEntity.status(HttpStatus.OK).body(eventShortDtoList);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(
            @PathVariable("eventId") Long eventId,
            HttpServletRequest request) {

        log.debug("GET publicFindById() with eventId: {}", eventId);
        request.setAttribute("app_name", "main application");
        sendToStats(request);
        EventFullDto eventFullDto = eventService.publicFindById(eventId);

        return ResponseEntity.status(HttpStatus.OK).body(eventFullDto);
    }

    private void sendToStats(HttpServletRequest servletRequest) {
        log.debug("Sending request to stats");
        try {
            statsClient.hit(servletRequest);
        } catch (RestClientException e) {
            log.error("Can't connect to the statistics server: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
