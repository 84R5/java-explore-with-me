package ru.practicum.main.stats.server.mapper;


import ru.practicum.main.stats.server.model.Hit;
import ru.practicum.main.stats.dto.StatsDtoRequest;
import ru.practicum.main.stats.dto.StatsDtoResponse;

public class HitMapper {

    public static Hit statsDtoRequestToHit(StatsDtoRequest statsDtoRequest) {
        return  Hit.builder()
                .app(statsDtoRequest.getApp())
                .uri(statsDtoRequest.getUri())
                .ip(statsDtoRequest.getIp())
                .build();
    }

    public static StatsDtoResponse hitToStatsDtoResponse(Hit hit) {
        return StatsDtoResponse.builder()
                .app(hit.getApp())
                .uri(hit.getUri())
                .build();
    }


    /*HitMapper INSTANCE = Mappers.getMapper(HitMapper.class);

    Hit statsDtoRequestToHit(StatsDtoRequest statsDtoRequest);

    StatsDtoResponse hitToStatsDtoResponse(Hit hit);*/
}
