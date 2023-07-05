package ru.practicum.category.mapper;

import ru.practicum.category.dto.CategoryDtoRequest;
import ru.practicum.category.dto.CategoryDtoResponse;
import ru.practicum.category.model.Category;

public class CategoryMapper {

    public static Category categoryDtoRequestToCategory(CategoryDtoRequest c){

        return Category.builder()
                .id(c.getId())
                .name(c.getName())
                .build();
    }

    public static CategoryDtoResponse categoryToCategoryDtoResponse(Category c){

        return CategoryDtoResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .build();
    }
}
