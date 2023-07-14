package ru.practicum.request.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.request.enums.RequestStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateRequest {

    @NotNull
    List<Long> requestIds;

    @NotBlank
    RequestStatus status;

}

