package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.model.Hit;

@UtilityClass
public class HitMapper {

    public static Hit statsDtoRequestToHit(StatsDtoRequest statsDtoRequest) {
        return Hit.builder()
                .app(statsDtoRequest.getApp())
                .uri(statsDtoRequest.getUri())
                .ip(statsDtoRequest.getIp())
                .timestamp(statsDtoRequest.getTimestamp())
                .build();
    }
}
