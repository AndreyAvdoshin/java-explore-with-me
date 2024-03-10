package ru.practicum.ewm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.SearchAdminParameters;
import ru.practicum.ewm.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/events")
public class EventController {

    public final EventService service;

    public EventController(EventService eventService) {
        this.service = eventService;
    }

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam @Valid SearchAdminParameters params) {
        log.info("Запрос админа на предоставление событий по параметрам - {}", params);
        return service.getEventsByAdmin(params);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@RequestBody @Valid UpdateEventAdminRequest updateRequest,
                                    @PathVariable @Positive Long eventId) {
        log.info("Запрос администратором на изменение события по id - {} - {}", eventId, updateRequest);
        return service.updateEventByIdAdmin(eventId, updateRequest);
    }
}
