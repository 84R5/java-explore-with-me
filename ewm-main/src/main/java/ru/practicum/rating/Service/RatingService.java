package ru.practicum.rating.Service;

import ru.practicum.Comment.dto.CommentDto;
import ru.practicum.event.model.Event;
import ru.practicum.rating.dto.RatingDto;
import ru.practicum.user.model.User;

public interface RatingService {

    RatingDto manageRating(User user, Event event, Integer rate, CommentDto dto);

}
