package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserDtoResponse {

    Long id;

    String name;

    String email;
}
