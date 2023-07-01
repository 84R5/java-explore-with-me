package ru.practicum.main.stats.server.service;

import ru.practicum.main.stats.dto.StatsDtoRequest;
import ru.practicum.main.stats.dto.StatsDtoResponse;


import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    StatsDtoResponse create(StatsDtoRequest dtoRequest);

    List<StatsDtoResponse> getStats(LocalDateTime start, LocalDateTime end, List<String> uri, boolean unique);

}

