package ru.practicum.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

@UtilityClass
public class UserMapper {
    public static User toUser(NewUserRequest newUserRequest) {
        return User.builder()
                .id(newUserRequest.getId())
                .name(newUserRequest.getName())
                .email(newUserRequest.getEmail())
                .build();
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName()
        );
    }

    public static UserDtoResponse userToUserDtoResponse(User user) {
        return UserDtoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}