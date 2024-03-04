package ru.practicum.ewm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.exception.DateTimeViolationException;
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

        return events.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventByIdAndUserId(Long eventId, Long userId) {
        Event event = checkEventBelongUser(eventId, userId);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto updateEventByIdAndUSerId(Long eventId, Long userId) {

    }


    private void checkEvent(Event event) {
        if (!event.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
            throw new IncorrectParameterException("eventDate",
                    "Начало события должно быть не раньше, чем за 2 часа от текущего времени");
        } else if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new IncorrectParameterException("eventDate",
                    "Начало события не может быть в прошлом");
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
}
