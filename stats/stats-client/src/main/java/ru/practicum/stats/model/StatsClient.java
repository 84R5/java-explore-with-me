package ru.practicum.stats.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.main.dto.StatsDtoRequest;
import ru.practicum.stats.client.BaseClient;

import java.util.Map;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PUBLIC)
public class StatsClient extends BaseClient {
    @Autowired
    StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    ResponseEntity<Object> hit(StatsDtoRequest requestDto) {
        return post("/hit", null, null, requestDto);
    }

    public ResponseEntity<Object> stats(String start,
                                        String end,
                                        String[] uris,
                                        boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        return patch("/stats?start={start}&end={end}&uris={uris}&unique={unique}", null, parameters, null);
    }

}