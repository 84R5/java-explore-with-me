package ru.practicum.Comment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "text")
    String text;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", nullable = false)
    User creator;
    @Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP")
    LocalDateTime created;

}
