package ru.practicum.ewm.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.model.State;

import java.time.LocalDateTime;

@Data
@Builder
public class ParticipationRequestDto {
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private State status;
}
