package ru.practicum.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {

    List<Long> events;
    Boolean pinned;
    @NotBlank
    @Length(min = 1, max = 50, message = "maxLength: 50, minLength: 1")
    String title;

}
