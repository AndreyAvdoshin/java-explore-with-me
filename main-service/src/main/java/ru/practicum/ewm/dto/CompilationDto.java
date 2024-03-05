package ru.practicum.ewm.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CompilationDto {

    private Set<EventShortDto> events;

    private Long id;

    private boolean pinned;

    private String title;
}
