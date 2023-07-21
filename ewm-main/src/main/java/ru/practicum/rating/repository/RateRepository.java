package ru.practicum.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.rating.model.Rating;

import java.util.Optional;


@Repository
public interface RateRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUserIdAndEventId(Long userId, Long eventId);

    @Query(value = "SELECT AVG(r.rate) FROM rates r WHERE r.event_id = ?1" , nativeQuery = true)
    Double getAvgRateByEventId(Long eventID);

    @Query(value = "SELECT AVG(r.rate) FROM rates r WHERE r.initiator_id = ?1" , nativeQuery = true)
    Double getAvgRateByEventInitiatorId(Long initiatorId);

}
