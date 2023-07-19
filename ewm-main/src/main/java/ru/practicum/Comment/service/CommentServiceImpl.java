package ru.practicum.Comment.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.Comment.dto.CommentDto;
import ru.practicum.Comment.mapper.CommentMapper;
import ru.practicum.Comment.model.Comment;
import ru.practicum.Comment.repository.CommentRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;

    public Comment create(Long userId, CommentDto dto) {
        return commentRepository.save(CommentMapper.commentRequestDtoToComment(dto, userId));
    }

}
