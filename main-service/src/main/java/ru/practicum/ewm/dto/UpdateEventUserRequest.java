package ru.practicum.ewm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.ewm.model.StateAction;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UpdateEventUserRequest extends NewEventDto {
    private StateAction stateAction;
}
