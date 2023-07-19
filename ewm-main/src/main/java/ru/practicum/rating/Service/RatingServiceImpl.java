package ru.practicum.rating.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Comment.dto.CommentDto;
import ru.practicum.Comment.service.CommentService;
import ru.practicum.event.model.Event;
import ru.practicum.rating.mapper.RatingMapper;
import ru.practicum.rating.model.CombineRatingId;
import ru.practicum.rating.model.Rating;
import ru.practicum.rating.repository.RateRepository;
import ru.practicum.user.model.User;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingServiceImpl implements RatingService {

    RateRepository rateRepository;
    CommentService commentService;

    @Override
    public Object manageEstimate(User user, Event event, Integer rate, CommentDto dto) {
        CombineRatingId id = CombineRatingId.builder().user(user).event(event).build();

        Rating rating = rateRepository
                .save(RatingMapper.requestToRating(id, rate, commentService.create(user.getId(), dto)
                ));

        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }

}
