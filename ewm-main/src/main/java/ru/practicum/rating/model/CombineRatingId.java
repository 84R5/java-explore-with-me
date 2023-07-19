package ru.practicum.rating.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CombineRatingId implements Serializable {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    Event event;

}
