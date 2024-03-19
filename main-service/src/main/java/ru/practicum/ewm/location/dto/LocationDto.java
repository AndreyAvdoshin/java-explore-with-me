package ru.practicum.ewm.location.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LocationDto {

    private Long id;
    private Double lat;
    private Double lon;
    private Float radius;
    private String name;
}
