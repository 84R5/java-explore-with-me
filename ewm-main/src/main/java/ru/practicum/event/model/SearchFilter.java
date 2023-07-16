package ru.practicum.event.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchFilter {
    List<Long> userIds;
    List<EventState> states;
    List<Long> categoryIds;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;

    String text;
    Boolean paid;
    Boolean onlyAvailable;

}
