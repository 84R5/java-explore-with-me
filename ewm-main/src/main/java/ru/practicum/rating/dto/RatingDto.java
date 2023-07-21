package ru.practicum.rating.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingDto {

    Long userId;

    Long eventId;

    Integer rate;

}
