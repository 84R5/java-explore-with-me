package ru.practicum.event.mapper;

import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.*;
import ru.practicum.event.enums.AdminActionState;
import ru.practicum.event.enums.EventState;
import ru.practicum.event.enums.StatePrivate;
import ru.practicum.event.model.Event;
import ru.practicum.locations.mapper.LocationMapper;
import ru.practicum.user.mapper.UserMapper;

import java.time.LocalDateTime;

public class EventMapper {

    public static Event newEventDtoRequestToEvent(NewEventDto request) {
        return Event.builder()
                .annotation(request.getAnnotation())
                .createdOn(LocalDateTime.now())
                .description(request.getDescription())
                .category(Category.builder().id(request.getCategory()).build())
                .eventDate(request.getEventDate())
                .location(LocationMapper.toLocation(request.getLocation()))
                .paid(request.getPaid())
                .confirmedRequests(0)
                .participantLimit(request.getParticipantLimit())
                .requestModeration(request.getRequestModeration())
                .state(EventState.PENDING)
                .title(request.getTitle())
                .build();
    }


    public static Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .id(newEventDto.getId())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .location(LocationMapper.toLocation(newEventDto.getLocation()))
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.categoryToCategoryDtoResponse(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.categoryToCategoryDtoResponse(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event updateEventFromAdmin(UpdateEventAdminRequest dto, Event e) {
        return Event.builder()
                .id(e.getId())
                .confirmedRequests(e.getConfirmedRequests())
                .category(dto.getCategory() != null ?
                        Category.builder().id(dto.getCategory()).build() : e.getCategory())
                .publishedOn(e.getPublishedOn())
                .state(dto.getStateAction() != null ?
                        dto.getStateAction().equals(AdminActionState.PUBLISH_EVENT) ?
                                EventState.PUBLISHED : EventState.CANCELED : e.getState())
                .initiator(e.getInitiator())
                .createdOn(e.getCreatedOn())
                .title(dto.getTitle() != null ? dto.getTitle() : e.getTitle())
                .participantLimit(dto.getParticipantLimit() != null ?
                        dto.getParticipantLimit() : e.getParticipantLimit())
                .location(e.getLocation())
                .paid(dto.getPaid() != null ?
                        dto.getPaid() : e.getPaid())
                .eventDate(dto.getEventDate() != null ?
                        dto.getEventDate() : e.getEventDate())
                .description(dto.getDescription() != null ?
                        dto.getDescription() : e.getDescription())
                .requestModeration(dto.getRequestModeration() != null ?
                        dto.getRequestModeration() : e.getRequestModeration())
                .annotation(dto.getAnnotation() != null ?
                        dto.getAnnotation() : e.getAnnotation())
                .views(e.getViews())
                .build();
    }

    public static Event updateEventFromUser(UpdateEventUserRequest dto, Event e) {
        return Event.builder()
                .id(e.getId())
                .confirmedRequests(e.getConfirmedRequests())
                .category(dto.getCategory() != null ?
                        Category.builder().id(dto.getCategory()).build() : e.getCategory())
                .publishedOn(e.getPublishedOn())
                .state(dto.getStateAction() != null ?
                        dto.getStateAction().equals(StatePrivate.SEND_TO_REVIEW) ?
                                EventState.PENDING : EventState.CANCELED : e.getState())
                .initiator(e.getInitiator())
                .createdOn(e.getCreatedOn())
                .title(dto.getTitle() != null ? dto.getTitle() : e.getTitle())
                .participantLimit(dto.getParticipantLimit() != null ?
                        dto.getParticipantLimit() : e.getParticipantLimit())
                .location(e.getLocation())
                .paid(dto.getPaid() != null ?
                        dto.getPaid() : e.getPaid())
                .eventDate(dto.getEventDate() != null ?
                        dto.getEventDate() : e.getEventDate())
                .description(dto.getDescription() != null ?
                        dto.getDescription() : e.getDescription())
                .requestModeration(dto.getRequestModeration() != null ?
                        dto.getRequestModeration() : e.getRequestModeration())
                .annotation(dto.getAnnotation() != null ?
                        dto.getAnnotation() : e.getAnnotation())
                .views(e.getViews())
                .build();
    }

}
