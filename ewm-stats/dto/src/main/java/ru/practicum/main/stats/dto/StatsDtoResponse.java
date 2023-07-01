package ru.practicum.main.stats.dto;

import lombok.Builder;

@Builder
public class StatsDtoResponse {
    String app;
    String uri;
    Long hits;
}
