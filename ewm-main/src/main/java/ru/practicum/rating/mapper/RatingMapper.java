package ru.practicum.rating.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.Comment.model.Comment;
import ru.practicum.rating.model.Rating;

@UtilityClass
public class RatingMapper {

    public static Rating requestToRating(Long userId,Long eventId, Integer rate, Comment dto) {
        return Rating.builder()
                .eventId(eventId)
                .userId(userId)
                .rate(rate)
                .comment(dto)
                .build();
    }

}
