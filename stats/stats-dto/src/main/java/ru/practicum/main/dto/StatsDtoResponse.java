package ru.practicum.main.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public record StatsDtoResponse(String app, String uri, Long hits) {
}
