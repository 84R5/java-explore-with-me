package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDtoRequest;
import ru.practicum.category.dto.CategoryDtoResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryDtoResponse> findAll(Integer from, Integer size);

    CategoryDtoResponse findById(Long categoryId);

    CategoryDtoResponse create(CategoryDtoRequest dto);

    CategoryDtoResponse update(Long categoryId, CategoryDtoRequest dto);

    void remove(Long catId);

}
