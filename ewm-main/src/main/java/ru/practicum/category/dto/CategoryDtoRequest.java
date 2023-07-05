package ru.practicum.category.dto;


import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class CategoryDtoRequest {

    Long id;

    @NotBlank
    String name;
}
