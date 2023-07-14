package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.practicum.locations.dto.LocationDto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    Long id;

    @Length(min = 20, max = 2000)
    @NotBlank
    String annotation;

    @NotNull
    Long category;

    @Length(min = 20, max = 7000)
    @NotBlank
    String description;

    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @NotNull
    LocationDto location;

    Boolean paid = false;

    Integer participantLimit = 0;

    Boolean requestModeration = true;

    @Size(min = 3, max = 120)
    @NotBlank
    String title;
}
