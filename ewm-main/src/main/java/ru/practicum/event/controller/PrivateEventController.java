package ru.practicum.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Comment.dto.CommentDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.service.EventService;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.request.model.EventRequestStatusUpdateResult;
import ru.practicum.request.service.RequestService;
import ru.practicum.util.ValidateManager;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrivateEventController {

    EventService eventService;

    RequestService requestService;


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<EventFullDto> create(
            @PathVariable("userId") Long userId,
            @RequestBody @Valid NewEventDto newEventDto) {

        log.debug("POST add() with {}", newEventDto);
        EventFullDto eventFullDto = eventService.create(userId, newEventDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventFullDto);
    }

    @GetMapping
    ResponseEntity<List<EventShortDto>> findAllByInitiatorId(
            @PathVariable("userId") Long initiatorId,
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {

        log.debug("GET findAllByInitiatorId() with initiatorId: {}, from: {}, size: {}", initiatorId, from, size);
        List<EventShortDto> eventShortDtoList = eventService.findById(initiatorId, from, size);

        return ResponseEntity.status(HttpStatus.OK).body(eventShortDtoList);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> findById(
            @PathVariable("userId") Long initiatorId,
            @PathVariable("eventId") Long eventId) {

        log.debug("GET findById() with initiatorId: {}, eventId: {}", initiatorId, eventId);
        EventFullDto eventFullDto = eventService.findById(initiatorId, eventId);

        return ResponseEntity.status(HttpStatus.OK).body(eventFullDto);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateById(
            @PathVariable("userId") Long initiatorId,
            @PathVariable("eventId") Long eventId,
            @RequestBody @Valid UpdateEventUserRequest dto) {

        log.debug("PATCH updateById() with initiatorId: {}, eventId: {}, dto: {}", initiatorId, eventId, dto);
        EventFullDto eventFullDto = eventService.updateByIdInitiator(initiatorId, eventId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(eventFullDto);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> findRequestsByEventId(
            @PathVariable Long userId,
            @PathVariable Long eventId) {

        log.debug("GET findRequestsByEventId() with initiatorId: {}, eventId: {}", userId, eventId);
        List<ParticipationRequestDto> requestDtoList = requestService.findRequestsByEventId(userId, eventId);

        return ResponseEntity.status(HttpStatus.OK).body(requestDtoList);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateStatusRequests(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody EventRequestStatusUpdateRequest dto) {

        log.debug("PATCH updateRequestState() with initiatorId: {}, eventId: {}, dto: {}", userId, eventId, dto);
        EventRequestStatusUpdateResult updateResult = requestService.updateStatusRequests(userId, eventId, dto);

        return updateResult;
    }

    @PostMapping("/{eventId}/rating")
    public ResponseEntity<Object> manageEstimate(
            @RequestBody @Valid CommentDto commentDto,
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestParam(name = "rate", required = false) Integer rate
    ){
        ValidateManager.checkRate(rate);

        Object rating = eventService.manageEstimate(userId,eventId,rate,commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(rating);
    }



}
