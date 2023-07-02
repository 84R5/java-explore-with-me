package ru.practicum.service;

import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.dto.StatsDtoResponse;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    Hit create(StatsDtoRequest dtoRequest);

    List<StatsDtoResponse> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}

