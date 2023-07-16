package ru.practicum.request.service;

import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.request.model.EventRequestStatusUpdateResult;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto create(Long userId, Long eventId);

    List<ParticipationRequestDto> findRequestsByEventId(Long userId, Long eventId);

    List<ParticipationRequestDto> findAllRequests(Long userId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    EventRequestStatusUpdateResult updateStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest request);

}
