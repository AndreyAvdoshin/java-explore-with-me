package ru.practicum.ewm.dto;

import lombok.Data;
import ru.practicum.ewm.model.State;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private State status;
}
