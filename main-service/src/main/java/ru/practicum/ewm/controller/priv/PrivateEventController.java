package ru.practicum.ewm.controller.priv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final EventService service;

    public PrivateEventController(EventService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@RequestBody @Valid NewEventDto newEventDto,
                                    @PathVariable @Positive Long userId) {
        return service.createEvent(newEventDto, userId);
    }
}
