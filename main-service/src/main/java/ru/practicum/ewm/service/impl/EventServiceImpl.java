package ru.practicum.ewm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.exception.IncorrectParameterException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.NotOwnerException;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.CategoryService;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final UserService userService;
    private final CategoryService categoryService;

    public EventServiceImpl(EventRepository repository, UserService userService,
                            CategoryService categoryService) {
        this.repository = repository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public EventFullDto createEvent(NewEventDto newEventDto, Long userId) {
        Event event = EventMapper.toEvent(newEventDto);
        checkEvent(event);

        User initiator = userService.returnIfExists(userId);
        Category category = categoryService.returnIfExists(newEventDto.getCategory());

        event.setInitiator(initiator);
        event.setCategory(category);
        event = repository.save(event);

        log.info("Сохранение события - {}", event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, int from, int size) {
        userService.checkExistingUser(userId);
        PageRequest page = PageRequest.of(from, size);
        List<Event> events = repository.findAllByInitiatorId(userId, page);
        log.info("Получение событий - {}", events);
        return events.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventByIdAndUserId(Long eventId, Long userId) {
        Event event = checkEventBelongUser(eventId, userId);
        log.info("Получение события - {}", event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto updateEventByIdAndUserId(Long eventId, Long userId,
                                                 UpdateEventUserRequest updateEventUserRequest) {
        Event event = checkEventBelongUser(eventId, userId);
        updateEvent(event, updateEventUserRequest, false);
        log.info("Опновление события - {}", event);
        return EventMapper.toEventFullDto(repository.save(event));

    }

    @Override
    public EventFullDto updateEventByIdAdmin(Long eventId, UpdateEventAdminRequest updateRequest) {
        Event event = returnIfExists(eventId);
        updateEvent(event, updateRequest, true);
        return EventMapper.toEventFullDto(repository.save(event));
    }


    private void checkEvent(Event event) {
        if (!event.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
            throw new IncorrectParameterException("eventDate",
                    "Начало события должно быть не раньше, чем за 2 часа от текущего времени");
        } else if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new IncorrectParameterException("eventDate", "Начало события не может быть в прошлом");
        }
    }

    private Event returnIfExists(Long eventId) {
        return repository.findById(eventId).orElseThrow(() -> new NotFoundException("Event", eventId));
    }

    private Event checkEventBelongUser(Long eventId, Long userId) {
        userService.checkExistingUser(userId);
        Event event = returnIfExists(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new NotOwnerException("Пользователь по id - " + userId + " не является содателем события по id - " +
                    userId);
        }
        return event;
    }

    private void updateEvent(Event event, UpdateEventUserRequest updateRequest, boolean isAdmin) {

        if (event.getState() == State.PUBLISHED) {
            throw new NotFoundException("Событие уже опубликовано и не может быть изменено");
        }

        if (updateRequest.getEventDate() != null && !isAdmin &&
                updateRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IncorrectParameterException("eventDate",
                    "Начало события должно быть не раньше, чем за 2 часа от текущего времени");
        } else if (updateRequest.getEventDate() != null &&
                updateRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new IncorrectParameterException("eventDate",
                    "Начало события должно быть не раньше, чем за 1 час от текущего времени");
        } else if (updateRequest.getEventDate() != null) {
            event.setEventDate(updateRequest.getEventDate());
        }

        event.setParticipantLimit(updateRequest.getParticipantLimit() != null ?
                updateRequest.getParticipantLimit() : event.getParticipantLimit());

        event.setAnnotation(updateRequest.getAnnotation() != null ?
                updateRequest.getAnnotation() : event.getAnnotation());

        event.setCategory(updateRequest.getCategory() != null ?
                categoryService.returnIfExists(updateRequest.getCategory()) : event.getCategory());

        event.setDescription(updateRequest.getDescription() != null ?
                updateRequest.getDescription() : event.getDescription());

        event.setLocation(updateRequest.getLocation() != null ?
                updateRequest.getLocation() : event.getLocation());

        event.setPaid(updateRequest.getPaid() != null ?
                updateRequest.getPaid() : event.isPaid());

        event.setRequestModeration(updateRequest.getRequestModeration() != null ?
                updateRequest.getRequestModeration() : event.isRequestModeration());

        event.setTitle(updateRequest.getTitle() != null ?
                updateRequest.getTitle() : event.getTitle());

        // Проверка и изменение статусов
        if (updateRequest.getStateAction() != null && isAdmin) {
            switch (updateRequest.getStateAction()) {
                case PUBLISH_EVENT:
                    if (event.getState() != State.PENDING) {
                        throw new IncorrectParameterException("State", "Данное событие не готово к публикации");
                    }
                    event.setState(State.PUBLISHED);
                    break;
                case REJECT_EVENT:
                    event.setState(State.CANCELED);
            }
        } else if (updateRequest.getStateAction() != null) {
            switch (updateRequest.getStateAction()) {
                case CANCEL_REVIEW:
                    event.setState(State.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(State.PENDING);
            }
        }
    }
}
