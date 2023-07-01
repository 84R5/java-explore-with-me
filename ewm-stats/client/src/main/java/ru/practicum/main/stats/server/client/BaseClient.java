package ru.practicum.main.stats.server.client;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseClient {
    final RestTemplate restTemplate;

    ResponseEntity<Object> get(String path) {
        return get(path, null, null);
    }

    ResponseEntity<Object> get(String path, long userId) {
        return get(path, userId, null);
    }

    ResponseEntity<Object> get(String path, Long userId, @Nullable Map<String, Object> param) {
        return makeAndSendRequest(HttpMethod.GET, path, userId, param, null);
    }

    ResponseEntity<Object> get(String path, @Nullable Map<String, Object> param) {
        return makeAndSendRequest(HttpMethod.GET, path, null, param, null);
    }

    <T> ResponseEntity<Object> post(String path, T body) {
        return post(path, null, null, body);
    }

    <T> ResponseEntity<Object> post(String path, long userId, T body) {
        return post(path, userId, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, Long userId, @Nullable Map<String, Object> param, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, userId, param, body);
    }

    <T> ResponseEntity<Object> put(String path, long userId, T body) {
        return put(path, userId, null, body);
    }

    <T> ResponseEntity<Object> put(String path, long userId, @Nullable Map<String, Object> param, T body) {
        return makeAndSendRequest(HttpMethod.PUT, path, userId, param, body);
    }

    <T> ResponseEntity<Object> patch(String path, T body) {
        return patch(path, null, null, body);
    }

    <T> ResponseEntity<Object> patch(String path, long userId) {
        return patch(path, userId, null, null);
    }

    <T> ResponseEntity<Object> patch(String path, long userId, T body) {
        return patch(path, userId, null, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, Long userId, @Nullable Map<String, Object> param, T body) {
        return makeAndSendRequest(HttpMethod.PATCH, path, userId, param, body);
    }

    ResponseEntity<Object> delete(String path) {
        return delete(path, null, null);
    }

    ResponseEntity<Object> delete(String path, long userId) {
        return delete(path, userId, null);
    }

    ResponseEntity<Object> delete(String path, Long userId, @Nullable Map<String, Object> param) {
        return makeAndSendRequest(HttpMethod.DELETE, path, userId, param, null);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, Long userId, @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders(userId));

        ResponseEntity<Object> shareitServerResponse;
        try {
            if (parameters != null) {
                shareitServerResponse = restTemplate.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                shareitServerResponse = restTemplate.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(shareitServerResponse);
    }

    private HttpHeaders defaultHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (userId != null) {
            headers.set("X-Sharer-User-Id", String.valueOf(userId));
        }
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }

}
