package ru.practicum.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.user.dto.UserDroRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.model.User;

@UtilityClass
public class UserMapper {

    public static User userRequestDtoToUser(UserDroRequest userDroRequest) {
        return User.builder()
                .id(userDroRequest.getId())
                .name(userDroRequest.getName())
                .email(userDroRequest.getEmail())
                .build();
    }

    public static UserDtoResponse userToUserDtoResponse(User user) {
        return UserDtoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}
