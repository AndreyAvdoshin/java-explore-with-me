package ru.practicum.ewm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventShortDto {

    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private int views;
}
