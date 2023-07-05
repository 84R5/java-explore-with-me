package ru.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.dto.StatsDtoResponse;
import ru.practicum.mapper.HitMapper;
import ru.practicum.mapper.HitStatMapper;
import ru.practicum.model.Hit;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    @Autowired
    private HitRepository hitRepository;

    @Override
    @Transactional
    public Hit create(StatsDtoRequest dtoRequest) {

        Hit hit = HitMapper.statsDtoRequestToHit(dtoRequest);
        hit = hitRepository.save(hit);
        log.info("Просмотр {} записан в базу", hit);
        return hit;
    }

    @Override
    public List<StatsDtoResponse> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        if (unique) {
            log.info("Статистика уникальных просмотров получена");
            return hitRepository.countingUniqueHits(start, end, uris)
                    .stream().map(HitStatMapper::hitStatToStatsDtoResponse).collect(Collectors.toList());

        } else {
            log.info("Статистика просмотров получена");
            System.out.println(hitRepository.countingHits(start, end, uris));
            return hitRepository.countingHits(start, end, uris)
                    .stream().map(HitStatMapper::hitStatToStatsDtoResponse).collect(Collectors.toList());
        }
    }
}
