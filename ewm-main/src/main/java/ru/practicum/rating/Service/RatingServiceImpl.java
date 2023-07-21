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
import ru.practicum.event.repository.EventRepository;
import ru.practicum.rating.dto.RatingDto;
import ru.practicum.rating.mapper.RatingMapper;
import ru.practicum.rating.model.Rating;
import ru.practicum.rating.repository.RateRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingServiceImpl implements RatingService {

    RateRepository rateRepository;
    UserRepository userRepository;
    EventRepository eventRepository;
    CommentService commentService;

    @Override
    public ResponseEntity<RatingDto> manageRating(User user, Event event, Integer rate, CommentDto dto) {

        rateRepository.findByUserIdAndEventId(user.getId(), event.getId()).ifPresent(rateRepository::delete);

        if (rate == null) {
            setRate(event);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        Rating rating = RatingMapper
                .requestToRating(user.getId(), event.getId(), event.getInitiator().getId(), rate, commentService.create(user.getId(), dto));

        rating = rateRepository.save(rating);
        setRate(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(RatingMapper.ratingToRatingDto(rating));
    }

    private void setRate(Event event) {
        event.setRate(rateRepository.getAvgRateByEventId(event.getId()));
        event.getInitiator().setRate(rateRepository.getAvgRateByEventInitiatorId(event.getInitiator().getId()));
        userRepository.save(event.getInitiator());
        eventRepository.save(event);
    }


}
