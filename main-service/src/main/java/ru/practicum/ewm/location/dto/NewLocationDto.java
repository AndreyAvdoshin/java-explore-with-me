package ru.practicum.ewm.location.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewLocationDto {

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

    private float radius;

    @NotBlank
    private String name;
}
