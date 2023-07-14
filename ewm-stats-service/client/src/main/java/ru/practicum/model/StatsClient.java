package ru.practicum.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.dto.StatsDtoResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@PropertySource(value = "classpath:application.properties")
public class StatsClient extends BaseClient {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public StatsClient(@Value("http://localhost:9090") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> hit(HttpServletRequest request
    ) {
        StatsDtoRequest hitDto = new StatsDtoRequest();
        hitDto.setApp((String) request.getAttribute("app_name"));
        hitDto.setUri(request.getRequestURI());
        hitDto.setIp(request.getRemoteAddr());
        hitDto.setTimestamp(LocalDateTime.now());
        return post("/hit", hitDto);
    }


    public List<StatsDtoResponse> stats(LocalDateTime start,
                                        LocalDateTime end,
                                        List<String> uris,
                                        Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", timeFormatter(start),
                "end", timeFormatter(end),
                "uris", String.join(",", uris),
                "unique", unique
        );
        ResponseEntity<Object> objects = get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        return objectMapper.convertValue(objects.getBody(), new TypeReference<>() {
        });
    }

    private String timeFormatter(LocalDateTime date) {
        return date.format(FORMAT);
    }

}