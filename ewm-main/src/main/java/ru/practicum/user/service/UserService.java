package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDtoResponse;

import java.util.List;

public interface UserService {

    List<UserDtoResponse> find(List<Long> userIds, Integer from, Integer size);

    UserDtoResponse create(NewUserRequest newUserRequest);

    void remove(Long userId);

}