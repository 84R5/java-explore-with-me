package ru.practicum.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoRequest {

    Long id;
    @Length(min = 1, max = 50, message = "maxLength: 50, minLength: 1")
    @NotBlank
    String name;

}