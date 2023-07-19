package ru.practicum.Comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.Comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
