package ru.practicum.rating.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingDto {


    Long userId;
    @NotNull
    Long eventId;
    @NotNull
    @NotBlank
    Integer rate;

}
