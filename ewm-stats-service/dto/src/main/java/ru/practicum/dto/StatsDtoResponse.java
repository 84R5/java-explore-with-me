package ru.practicum.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatsDtoResponse {

        String app;
        String uri;
        Long hits;

}
