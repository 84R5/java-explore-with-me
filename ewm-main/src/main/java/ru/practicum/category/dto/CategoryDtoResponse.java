package ru.practicum.category.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public class CategoryDtoResponse {
    Long id;

    String name;
}
