package ru.practicum.event.service;

import ru.practicum.Comment.dto.CommentDto;
import ru.practicum.event.dto.*;
import ru.practicum.event.enums.EventSort;
import ru.practicum.event.model.SearchFilter;
import ru.practicum.rating.dto.RatingDto;

import java.util.List;

public interface EventService {
    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto findById(Long userId, Long eventId);

    List<EventShortDto> findById(Long userId, Integer from, Integer size);

    EventFullDto updateByIdInitiator(Long userId, Long eventId, UpdateEventUserRequest eventUserRequest);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest eventAdminRequest);

    List<EventShortDto> searchUsingFilterAndSorting(SearchFilter filter, EventSort eventSort, Integer from, Integer size);

    List<EventFullDto> searchEventsFromAdmin(SearchFilter filter, Integer from, Integer size);

    EventFullDto publicFindById(Long eventId);

    RatingDto manageEstimate(Long userId, Long eventId, Integer rate, CommentDto commentDto);

}
