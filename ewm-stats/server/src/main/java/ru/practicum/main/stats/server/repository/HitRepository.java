package ru.practicum.main.stats.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.main.stats.server.model.Hit;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit,Long> {

    @Query(value =
            "SELECT app AS app, uri AS uri, COUNT(ip) AS views FROM Hit" +
                    " WHERE uri IN :uris AND (timestamp >= :start AND timestamp <= :end) GROUP BY app, uri"

    )
    List<Hit> countingHits(@Param("uris")Collection<String> uris,
                           @Param("start")LocalDateTime start,
                           @Param("end") LocalDateTime end);

    @Query(value =
            "SELECT app AS app, uri AS uri, COUNT(DISTINCT ip) AS views FROM Hit" +
                    " WHERE uri IN :uris AND (timestamp >= :start AND timestamp < :end) GROUP BY app, uri"
    )
    List<Hit> countingUniqueHits(@Param("uris") Collection<String> uris,
                                 @Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end);
}
