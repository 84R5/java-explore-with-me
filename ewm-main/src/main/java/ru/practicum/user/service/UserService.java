package ru.practicum.user.service;

import ru.practicum.user.dto.UserDroRequest;
import ru.practicum.user.dto.UserDtoResponse;

import java.util.List;

public interface UserService {

    UserDtoResponse create(UserDroRequest userDroRequest);

    List<UserDtoResponse> find(List<Long> userIds, Integer from, Integer size);

    void remove(Long userId);
}
