package ru.practicum.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Formula;
import ru.practicum.category.model.Category;
import ru.practicum.event.enums.EventState;
import ru.practicum.locations.model.Location;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "annotation", length = 2000, nullable = false)
    String annotation;

    @ManyToOne
    @JoinColumn(name = "categories", referencedColumnName = "id")
    Category category;

    @Column(name = "confirmed_request", nullable = false)
    Integer confirmedRequests;

    @Column(name = "created_on", nullable = false, columnDefinition = "TIMESTAMP")
    LocalDateTime createdOn;

    @Column(length = 7000, nullable = false)
    String description;

    @Column(name = "event_date", nullable = false, columnDefinition = "TIMESTAMP")
    LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false, referencedColumnName = "id")
    User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    Location location;

    @Column(nullable = false)
    Boolean paid;

    @Column(nullable = false)
    Integer participantLimit;

    @Column(name = "published_on", columnDefinition = "TIMESTAMP")
    LocalDateTime publishedOn;

    @Column(nullable = false)
    Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    EventState state;

    @Column(length = 120, nullable = false)
    String title;

    @Column
    Long views;

    @Formula("(SELECT AVG(r.rate) FROM rates r WHERE r.event_id = id)")
    Double rate;

}
