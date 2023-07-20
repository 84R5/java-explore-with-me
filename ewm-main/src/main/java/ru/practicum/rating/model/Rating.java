package ru.practicum.rating.model;

import lombok.*;
import ru.practicum.Comment.model.Comment;

import javax.persistence.*;

@Entity
@Table(name = "rates")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "rate")
    private Integer rate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
