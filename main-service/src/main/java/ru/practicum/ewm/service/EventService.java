package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.model.Event;

import java.util.List;

public interface EventService {

    EventFullDto createEvent(NewEventDto newEventDto, Long userId);

    List<EventShortDto> getEventsByUserId(Long userId, int from, int size);

    EventFullDto getEventByIdAndUserId(Long eventId, Long userId);

    EventFullDto updateEventByIdAndUserId(Long eventId, Long userId, UpdateEventUserRequest updateEventUserRequest);

    EventFullDto updateEventByIdAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    Event returnIfExists(Long eventId);

}
