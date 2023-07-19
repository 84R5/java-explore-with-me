package ru.practicum.Comment.service;

import ru.practicum.Comment.dto.CommentDto;
import ru.practicum.Comment.model.Comment;

public interface CommentService {
    Comment create(Long userId, CommentDto dto);
}
