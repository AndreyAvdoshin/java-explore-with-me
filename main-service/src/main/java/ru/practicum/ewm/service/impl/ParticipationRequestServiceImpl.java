package ru.practicum.ewm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.exception.ConflictParameterException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.ParticipationRequestService;
import ru.practicum.ewm.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository repository;
    private final UserService userService;
    private final EventService eventService;

    public ParticipationRequestServiceImpl(ParticipationRequestRepository repository, UserService userService,
                                           EventService eventService) {
        this.repository = repository;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        List<ParticipationRequest> requests = repository.findAllByRequesterId(userId);
        log.info("Возврат запросов пользователя по id - {} - {}", userId, requests);

        return requests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByEventId(Long eventId) {
        List<ParticipationRequest> requests = repository.findAllByEventId(eventId);
        log.info("Возврат запросов события по id - {} - {}", eventId, requests);

        return requests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Event event = eventService.returnIfExists(eventId);
        User requester = userService.returnIfExists(userId);

        if (event.getInitiator().equals(requester)) {
            throw new ConflictParameterException("userId", "Нельзя добавить запрос на собственное событие");
        } else if (event.getState() != State.PUBLISHED) {
            throw new ConflictParameterException("state", "Нельзя добавить запрос на неопубликованное событие");
        } else if (event.getParticipantLimit() != 0 &&
                repository.countAllByEventId(eventId) >= event.getParticipantLimit()) {
            throw new ConflictParameterException("participantLimit", "Превышен лимит на участие в событии");
        }

        ParticipationRequest participationRequest = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(requester)
                .status(event.isRequestModeration() ? State.PENDING : State.CONFIRMED)
                .build();

        log.info("Созранине запроса на участие в событии - {}", participationRequest);
        return ParticipationRequestMapper.toParticipationRequestDto(repository.save(participationRequest));
    }

    @Override
    public List<ParticipationRequestDto> updateRequestsStatusByEvent(Long eventId) {
        List<ParticipationRequest> requests = repository.findAllByEventId(eventId);

        return  null;

    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        userService.checkExistingUser(userId);
        ParticipationRequest request = returnIfExists(requestId);

        if (!request.getRequester().getId().equals(userId)) {
            throw new ConflictParameterException("requester", "Нельзя отменять не свой запрос на участие");
        } else {
            request.setStatus(State.PENDING);
        }

        log.info("Сохранение отмененного запроса на участие - {}", request);
        return ParticipationRequestMapper.toParticipationRequestDto(repository.save(request));

    }

    private ParticipationRequest returnIfExists(Long requestId) {
        return repository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("ParticipationRequest", requestId));
    }
}
