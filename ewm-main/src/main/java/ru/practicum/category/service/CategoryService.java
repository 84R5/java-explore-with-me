package ru.practicum.category.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import ru.practicum.category.dto.CategoryDtoRequest;
import ru.practicum.category.dto.CategoryDtoResponse;

import java.util.List;

public interface CategoryService {
    CategoryDtoResponse create(CategoryDtoRequest categoryDtoRequest);

    CategoryDtoResponse update(Long catId, CategoryDtoRequest categoryDtoRequest);

    void remove(Long catId);

    List<CategoryDtoResponse> findAll(Integer from, Integer size);

    CategoryDtoResponse findById(Long catId);
}
