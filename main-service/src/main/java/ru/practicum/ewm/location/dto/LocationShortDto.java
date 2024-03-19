package ru.practicum.ewm.location.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LocationShortDto {

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;
}
