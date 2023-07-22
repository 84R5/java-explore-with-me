package ru.practicum.category.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.category.dto.CategoryDtoRequest;
import ru.practicum.category.dto.CategoryDtoResponse;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.util.PageCalculate;
import ru.practicum.util.ValidateManager;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements ru.practicum.category.service.CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public List<CategoryDtoResponse> findAll(Integer from, Integer size) {
        return categoryRepository.findAll(PageCalculate.getPage(from, size))
                .map(CategoryMapper::categoryToCategoryDtoResponse).getContent();
    }

    @Override
    public CategoryDtoResponse findById(Long categoryId) {
        return CategoryMapper.categoryToCategoryDtoResponse(
                categoryRepository
                        .findById(categoryId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found."))
        );
    }

    @Override
    public CategoryDtoResponse create(CategoryDtoRequest dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name is already used.");
        }
        Category category = categoryRepository.save(CategoryMapper.categoryDtoRequestToCategory(dto));
        log.debug("New {} save: {}.", category.getClass().getSimpleName(), category);
        return CategoryMapper.categoryToCategoryDtoResponse(category);
    }

    @Override
    public CategoryDtoResponse update(Long categoryId, CategoryDtoRequest dto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found."));
        if (categoryRepository.existsByName(dto.getName()) && !dto.getName().equals(category.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name is already used.");
        }

        category.setName(dto.getName());
        categoryRepository.save(category);
        return CategoryMapper.categoryToCategoryDtoResponse(category);
    }

    @Override
    public void remove(Long catId) {
        ValidateManager.checkId(categoryRepository, catId);
        categoryRepository.deleteById(catId);
    }

}
