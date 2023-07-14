package ru.practicum.request.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/requests")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestController {

    RequestService requestService;

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> findAllRequests(
            @PathVariable Long userId) {

        log.debug("GET findAllRequests() with: userId={}", userId);
        List<ParticipationRequestDto> requestDtoList = requestService.findAllRequests(userId);

        return ResponseEntity.status(HttpStatus.OK).body(requestDtoList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ParticipationRequestDto> create(
            @PathVariable Long userId,
            @RequestParam Long eventId) {
        log.debug("POST create() with : userId={}, eventId={}", userId, eventId);
        ParticipationRequestDto requestDto = requestService.create(userId, eventId);

        return ResponseEntity.status(HttpStatus.CREATED).body(requestDto);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId) {

        log.info("PATCH cancelRequest() with: userId={}, requestId={}", userId, requestId);
        ParticipationRequestDto requestDto = requestService.cancelRequest(userId, requestId);

        return ResponseEntity.status(HttpStatus.OK).body(requestDto);
    }
}
