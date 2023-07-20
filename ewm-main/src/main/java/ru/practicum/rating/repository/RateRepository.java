package ru.practicum.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.rating.model.Rating;

import java.util.Optional;


@Repository
public interface RateRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUserIdAndEventId(Long userId, Long eventId);

}
