package ru.practicum.ewm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.exception.DateTimeViolationException;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.CategoryService;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

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

        CategoryDto categoryDto = CategoryMapper.toCategoryDto(category);
        UserShortDto userShortDto = UserMapper.toUserShortDto(initiator);


        log.info("Сохранение события - {}", event);
        return EventMapper.toEventFullDto(event, userShortDto, categoryDto);
    }

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, int from, int size) {
        userService.checkExistingUser(userId);
        PageRequest page = PageRequest.of(from, size);
        List<Event> events = repository.findAllByInitiatorId(userId, page);
        // необходимо написать перевод из собылия в список событий с коротким описанием

    }

    private void checkEvent(Event event) {
        if (!event.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
            throw new DateTimeViolationException("eventDate",
                    "Начало события должно быть не раньше, чем за 2 часа от текущего времени");
        } else if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new DateTimeViolationException("eventDate",
                    "Начало события не может быть в прошлом");
        }
    }
}
