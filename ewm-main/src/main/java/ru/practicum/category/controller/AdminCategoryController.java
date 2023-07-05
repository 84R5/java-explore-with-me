package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDtoRequest;
import ru.practicum.category.dto.CategoryDtoResponse;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDtoResponse> create(@Valid @RequestBody CategoryDtoRequest dto) {
        log.debug("POST add() with {}", dto);
        CategoryDtoResponse body = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDtoResponse> update(@Valid @RequestBody CategoryDtoRequest dto,
                                              @PathVariable Long catId) {
        log.debug("PATCH update() with {}", dto);
        CategoryDtoResponse body = categoryService.update(catId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<CategoryDtoResponse> remove(@PathVariable Long catId) {
        log.debug("DELETE remove() with userId: {}", catId);
        categoryService.remove(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
