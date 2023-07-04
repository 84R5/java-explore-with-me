package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Hit;
import ru.practicum.model.HitStat;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {


    @Query(" SELECT new ru.practicum.model.HitStat(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "AND (h.uri IN (?3) OR (?3) is NULL) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC "
    )
    List<HitStat> countingHits(LocalDateTime start, LocalDateTime end, List<String> uris);


    @Query(" SELECT new ru.practicum.model.HitStat(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "AND (h.uri IN (?3) OR (?3) is NULL) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC "
    )
    List<HitStat> countingUniqueHits(LocalDateTime start, LocalDateTime end, List<String> uris);
}
