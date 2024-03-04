package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.NewEventDto;

import java.util.List;

public interface EventService {

    EventFullDto createEvent(NewEventDto newEventDto, Long userId);

    List<EventShortDto> getEventsByUserId(Long userId, int from, int size);

    EventFullDto getEventByIdAndUserId(Long eventId, Long userId);

    EventFullDto updateEventByIdAndUSerId(Long eventId, Long userId);

}
