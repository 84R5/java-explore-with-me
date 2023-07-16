package ru.practicum.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class CategoryDtoResponse {

    Long id;
    @Length(min = 1, max = 50, message = "maxLength: 50, minLength: 1")
    String name;

}