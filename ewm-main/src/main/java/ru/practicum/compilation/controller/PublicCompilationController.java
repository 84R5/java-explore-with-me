package ru.practicum.compilation.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublicCompilationController {

    CompilationService compilationService;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> findAll(
            @RequestParam(value = "pinned", required = false) Boolean pined,
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {

        log.debug("GET findAll() with from: {}, size {}", from, size);
        List<CompilationDto> compilationDtoList = compilationService.findAll(pined, from, size);

        return ResponseEntity.status(HttpStatus.OK).body(compilationDtoList);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> findById(
            @PathVariable("compId") Long compId) {

        log.info("Получаем запрос на конкретную подборку: compilationId={}", compId);
        CompilationDto compilationDto = compilationService.findById(compId);

        return ResponseEntity.status(HttpStatus.OK).body(compilationDto);
    }
}
