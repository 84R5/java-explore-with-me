package ru.practicum.rating.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.Comment.model.Comment;
import ru.practicum.rating.model.CombineRatingId;
import ru.practicum.rating.model.Rating;

@UtilityClass
public class RatingMapper {

    public static Rating requestToRating(CombineRatingId id, Integer rate, Comment dto) {
        return Rating.builder()
                .id(id)
                .rate(rate)
                .comment(dto)
                .build();
    }

}
