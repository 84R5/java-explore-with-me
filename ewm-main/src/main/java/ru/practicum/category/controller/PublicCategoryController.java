package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.category.dto.CategoryDtoResponse;
import ru.practicum.category.service.CategoryService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDtoResponse>> findAll(
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAll() with from: {}, size {}", from, size);
        List<CategoryDtoResponse> found = categoryService.findAll(from, size);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDtoResponse> findById(@PathVariable("catId") Long catId) {
        CategoryDtoResponse found = categoryService.findById(catId);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

}
