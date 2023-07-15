package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.dto.StatsDtoResponse;
import ru.practicum.model.Hit;
import ru.practicum.service.StatsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<Hit> create(@RequestBody @Valid StatsDtoRequest requestDto,
                                      HttpServletRequest request) {
        log.info("Запрос к эндпоинту '{}' на добавление статистики {}", request.getRequestURI(), requestDto);
        return new ResponseEntity<>(statsService.create(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<StatsDtoResponse>> getStats(
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(defaultValue = "") List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique,
            HttpServletRequest request) {
        validateTimeParam(start, end);
        log.info("Запрос к эндпоинту '{}' на получение статистики", request.getRequestURI());
        return new ResponseEntity<>(statsService.getStats(start, end, uris, unique), HttpStatus.OK);
    }

    private void validateTimeParam(LocalDateTime s, LocalDateTime e) {
        if (s == null || e == null ||  s.isBefore(LocalDateTime.now()) || e.isBefore(s)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid time start or end");
        }
    }

}
