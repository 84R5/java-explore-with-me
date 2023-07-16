package ru.practicum.category.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDtoResponse;
import ru.practicum.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublicCategoryController {

    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDtoResponse>> findAll(
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {

        log.debug("GET findAll() with from: {}, size {}", from, size);
        List<CategoryDtoResponse> found = categoryService.findAll(from, size);

        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDtoResponse> findById(
            @PathVariable Long catId) {

        log.debug("GET findById(): catId = {}", catId);
        CategoryDtoResponse found = categoryService.findById(catId);

        return ResponseEntity.status(HttpStatus.OK).body(found);
    }
}
