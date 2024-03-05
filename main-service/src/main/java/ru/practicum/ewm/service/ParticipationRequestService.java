package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.ParticipationRequestDto;

public interface ParticipationRequestService {

    ParticipationRequestDto createRequest(Long userId, Long eventId);
}
