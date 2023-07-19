package ru.practicum.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.enums.EventState;
import ru.practicum.event.model.SearchFilter;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminEventController {

    EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventFullDto>> searchEvents(
            @RequestParam(value = "users", required = false) List<Long> userIds,
            @RequestParam(value = "states", required = false) List<EventState> states,
            @RequestParam(value = "categories", required = false) List<Long> categoryIds,
            @RequestParam(value = "rangeStart", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(value = "rangeEnd", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {

        if (rangeEnd != null && rangeStart != null && rangeEnd.isBefore(rangeStart)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rangeEnd cannot be earlier than the rangeStart");
        }

        SearchFilter filter = SearchFilter.builder()
                .userIds(userIds)
                .states(states)
                .categoryIds(categoryIds)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .build();

        log.debug("GET adminByFilter() filter:{}", filter);
        List<EventFullDto> eventFullDtoList = eventService.searchEventsFromAdmin(filter, from, size);

        return ResponseEntity.status(HttpStatus.OK).body(eventFullDtoList);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventByAdmin(
            @PathVariable Long eventId,
            @RequestBody @Valid UpdateEventAdminRequest eventAdminRequest) {

        log.debug("PATCH adminUpdateById() with eventId: {}, dto: {}", eventId, eventAdminRequest);
        EventFullDto eventFullDto = eventService.updateEventByAdmin(eventId, eventAdminRequest);

        return ResponseEntity.status(HttpStatus.OK).body(eventFullDto);
    }
}
