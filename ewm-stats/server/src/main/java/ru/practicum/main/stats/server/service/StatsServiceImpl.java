package ru.practicum.main.stats.server.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.stats.server.mapper.HitMapper;
import ru.practicum.main.stats.server.model.Hit;
import ru.practicum.main.stats.server.repository.HitRepository;
import ru.practicum.main.stats.dto.StatsDtoRequest;
import ru.practicum.main.stats.dto.StatsDtoResponse;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    @Autowired
    private HitRepository hitRepository;
    //private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public StatsDtoResponse create(StatsDtoRequest dtoRequest) {

        Hit hit = HitMapper.statsDtoRequestToHit(dtoRequest);
        hit.setTimestamp(LocalDateTime.now());
        hit = hitRepository.save(hit);
        log.info("Просмотр {} записан в базу", hit);
        return HitMapper.hitToStatsDtoResponse(hit);
    }

    @Override
    public List<StatsDtoResponse> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<Hit> hits;

        /*LocalDateTime timeStart = LocalDateTime.parse(start, formatter);
        LocalDateTime timeEnd = LocalDateTime.parse(end, formatter);*/

        if (unique) {
            hits = hitRepository.countingUniqueHits(uris,start,end);
            log.info("Статистика уникальных просмотров получена");
        } else {
            hits = hitRepository.countingHits(uris,start,end);
            log.info("Статистика просмотров получена");
        }

        return hits.stream()
                .map(HitMapper::hitToStatsDtoResponse)
                .collect(Collectors.toList());
    }
}
