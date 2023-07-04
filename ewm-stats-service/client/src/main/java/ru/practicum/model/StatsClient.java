package ru.practicum.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.main.client.BaseClient;
import ru.practicum.main.dto.StatsDtoRequest;

import java.util.List;
import java.util.Map;

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
        return post("/hit", requestDto);
    }

    ResponseEntity<Object> stats(String start,
                                 String end,
                                 List<String> uris,
                                 Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

}