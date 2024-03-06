package ru.practicum.ewm.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class NewCompilationDto {

    private Set<Long> events;

    private boolean pinned;

    @NotBlank
    @Length(min = 1, max = 50)
    private String title;
}