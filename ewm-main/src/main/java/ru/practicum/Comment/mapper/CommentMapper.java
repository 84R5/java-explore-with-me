package ru.practicum.Comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.Comment.dto.CommentDto;
import ru.practicum.Comment.model.Comment;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {

    public static Comment commentRequestDtoToComment(CommentDto dto, Long userId) {

        return Comment.builder()
                .text(dto.getText())
                .creator(User.builder().id(userId).build())
                .created(LocalDateTime.now())
                .build();
    }

}
