package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequests(Long userId);

    List<ParticipationRequestDto> getRequestsByEventId(Long eventId);

    List<ParticipationRequestDto> updateRequestsStatusByEvent(Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
