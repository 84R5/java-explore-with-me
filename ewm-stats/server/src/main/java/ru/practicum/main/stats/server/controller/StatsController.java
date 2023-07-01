package ru.practicum.main.stats.server.controller;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.stats.dto.StatsDtoRequest;
import ru.practicum.main.stats.dto.StatsDtoResponse;
import ru.practicum.main.stats.server.service.StatsService;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/hit")
    public StatsDtoResponse create(@RequestBody @Valid StatsDtoRequest requestDto,
                                   HttpServletRequest request) {
        log.info("Запрос к эндпоинту '{}' на добавление статистики {}", request.getRequestURI(), requestDto);
        return statsService.create(requestDto);
    }

    @GetMapping("/stats")
    public List<StatsDtoResponse> getStats(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(name = "uris", required = false) List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") boolean unique,
            HttpServletRequest request) {
        log.info("Запрос к эндпоинту '{}' на получение статистики",request.getRequestURI());
        return statsService.getStats(start,end,uris,unique);
    }
}
