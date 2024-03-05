package ru.practicum.ewm.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.ParticipationRequestService;

@Service
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestService service;

    public ParticipationRequestServiceImpl(ParticipationRequestService service) {
        this.service = service;
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        // написать логику проверок на различные ограничения
        // если собыие уже опубликовано и т.д.
        return null;
    }
}
