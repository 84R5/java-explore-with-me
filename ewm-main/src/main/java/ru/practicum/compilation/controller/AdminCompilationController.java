package ru.practicum.compilation.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminCompilationController {

    CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationDto> create(
            @RequestBody @Valid NewCompilationDto newCompilationDto) {

        log.debug("POST create() with {}.", newCompilationDto);
        CompilationDto compilationDto = compilationService.addCompilation(newCompilationDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(compilationDto);
    }

    @PatchMapping("/{compilationId}")
    public ResponseEntity<CompilationDto> update(
            @PathVariable Long compilationId,
            @RequestBody @Valid UpdateCompilationRequest compilationRequest) {

        log.debug("PATCH update() with {}", compilationRequest);
        CompilationDto compilationDto = compilationService.update(compilationId, compilationRequest);

        return ResponseEntity.status(HttpStatus.OK).body(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> remove(@PathVariable("compId") long compId) {

        log.debug("REMOVE remove() with userId: {}", compId);
        compilationService.remove(compId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
