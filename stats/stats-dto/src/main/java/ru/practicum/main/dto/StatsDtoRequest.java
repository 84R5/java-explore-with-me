package ru.practicum.main.dto;


import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Value
public record StatsDtoRequest(Long id, @NotBlank String app, @NotBlank String uri, @NotBlank String ip,
                              @NotNull LocalDateTime timestamp) {
}
