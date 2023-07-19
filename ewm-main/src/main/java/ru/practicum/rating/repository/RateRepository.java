package ru.practicum.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.rating.model.CombineRatingId;
import ru.practicum.rating.model.Rating;



@Repository
public interface RateRepository extends JpaRepository<Rating, CombineRatingId> {

}
