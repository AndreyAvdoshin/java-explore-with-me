package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.NewEventDto;

public interface EventService {

    EventFullDto createEvent(NewEventDto newEventDto, Long userId);
}
