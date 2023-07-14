package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    CompilationDto update(Long compilationId, UpdateCompilationRequest updateCompilationRequest);

    void remove(Long compilationId);

    CompilationDto findById(Long compilationId);

    List<CompilationDto> findAll(Boolean pined, Integer from, Integer size);


}
