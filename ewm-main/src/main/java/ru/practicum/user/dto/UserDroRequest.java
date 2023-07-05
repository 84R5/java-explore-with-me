package ru.practicum.user.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class UserDroRequest {

    Long id;

    @NotBlank
    String name;

    @NotNull
    @Email
    String email;
}
