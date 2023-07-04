package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatsDtoResponse;
import ru.practicum.model.HitStat;

@UtilityClass
public class HitStatMapper {

    public static StatsDtoResponse hitStatToStatsDtoResponse(HitStat hitStat) {
        return StatsDtoResponse.builder()
                .app(hitStat.getApp())
                .uri(hitStat.getUri())
                .hits(hitStat.getHits())
                .build();
    }
}
