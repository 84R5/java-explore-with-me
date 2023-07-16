package ru.practicum.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewUserRequest {

    Long id;
    @NotBlank
    @Length(min = 2, max = 250)
    String name;
    @NotNull
    @NotBlank
    @Email
    @Length(min = 6, max = 254)
    String email;

}