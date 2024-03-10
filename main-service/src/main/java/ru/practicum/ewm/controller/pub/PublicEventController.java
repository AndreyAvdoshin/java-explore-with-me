package ru.practicum.ewm.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.SearchParameters;
import ru.practicum.ewm.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/events")
public class PublicEventController {

    private final EventService service;

    public PublicEventController(EventService service) {
        this.service = service;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable @Positive Long eventId, HttpServletRequest request) {
        log.info("Публичный запрос события по id - {}", eventId);
        return service.getEventById(eventId, request);
    }

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam SearchParameters params, HttpServletRequest request) {
        log.info("Публичный запрос событий с параметрами - {}", params);
        return service.getEvents(params, request);
    }

}
