package ru.practicum.rating.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.Comment.model.Comment;
import ru.practicum.rating.dto.RatingDto;
import ru.practicum.rating.model.Rating;

@UtilityClass
public class RatingMapper {

    public static Rating requestToRating(Long userId,Long eventId,Long initiatorId,  Integer rate, Comment dto) {
        return Rating.builder()
                .eventId(eventId)
                .userId(userId)
                .initiatorId(initiatorId)
                .rate(rate)
                .comment(dto)
                .build();
    }

    public static RatingDto ratingToRatingDto(Rating rating){
        return RatingDto.builder()
                .userId(rating.getUserId())
                .eventId(rating.getEventId())
                .rate(rating.getRate())
                .build();
    }

}
