package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.model.Location;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Builder
public class NewEventDto {

    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;

    @NotNull
    @Positive
    private Long category;

    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;

    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    private boolean paid;

    @PositiveOrZero
    private int participantLimit;

    private boolean requestModeration;

    @NotBlank
    @Length(min = 3, max = 120)
    private String title;
}
