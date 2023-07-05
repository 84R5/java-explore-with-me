package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.category.dto.CategoryDtoRequest;
import ru.practicum.category.dto.CategoryDtoResponse;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.util.PageCalc;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDtoResponse create(CategoryDtoRequest categoryDtoRequest) {

        Category c = categoryRepository.save(CategoryMapper.categoryDtoRequestToCategory(categoryDtoRequest));
        log.debug("New {} save: {}.", c.getClass().getSimpleName(), c);

        return CategoryMapper.categoryToCategoryDtoResponse(c);
    }

    @Override
    @SneakyThrows
    public CategoryDtoResponse update(Long catId, CategoryDtoRequest categoryDtoRequest) {
        if(!categoryRepository.existsById(catId)) {
            throw new ChangeSetPersister.NotFoundException();
        }
            Category c = categoryRepository.save(CategoryMapper.categoryDtoRequestToCategory(categoryDtoRequest));
            log.debug("Update name {}: {}",c.getClass().getSimpleName(), c);
            return CategoryMapper.categoryToCategoryDtoResponse(c);
    }

    @Override
    @SneakyThrows
    public void remove(Long catId) {
        if(!categoryRepository.existsById(catId)) {
            throw new ChangeSetPersister.NotFoundException();
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public List<CategoryDtoResponse> findAll(Integer from, Integer size) {
        Pageable pageable = PageCalc.getPage(from,size);
        Page<Category> page = categoryRepository.findAll(pageable);
        log.debug("Found {}: {}.",Category.class.getSimpleName(),page.getTotalElements());
        return page.stream().map(CategoryMapper::categoryToCategoryDtoResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryDtoResponse findById(Long catId) {
        Category c = categoryRepository.findById(catId)
                .orElseThrow();
        return CategoryMapper.categoryToCategoryDtoResponse(c);
    }
}
