package ru.practicum.category.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminCategoryController {

    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDtoResponse> create(
            @RequestBody @Valid CategoryDtoRequest dto) {

        log.debug("POST create() with {}", dto);
        CategoryDtoResponse category = categoryService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDtoResponse> update(
            @PathVariable Long catId,
            @RequestBody @Valid CategoryDtoRequest dto) {

        log.debug("PATCH update() with {}", dto);
        CategoryDtoResponse category = categoryService.update(catId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<CategoryDtoResponse> remove(
            @PathVariable Long catId) {

        log.debug("DELETE remove() with userId: {}", catId);
        categoryService.remove(catId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
