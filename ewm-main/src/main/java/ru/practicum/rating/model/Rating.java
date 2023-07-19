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

    @EmbeddedId
    private CombineRatingId id;

    @Column(name = "rate")
    private Integer rate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
