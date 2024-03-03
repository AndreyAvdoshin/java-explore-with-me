package ru.practicum.ewm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.UserShortDto;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.CategoryService;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.UserService;

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
        User initiator = userService.returnIfExists(userId);
        Category category = categoryService.returnIfExists(newEventDto.getCategory());
        Event event = EventMapper.toEvent(newEventDto);

        event.setInitiator(initiator);
        event.setCategory(category);
        event = repository.save(event);

        CategoryDto categoryDto = CategoryMapper.toCategoryDto(category);
        UserShortDto userShortDto = UserMapper.toUserShortDto(initiator);


        log.info("Сохранение события - {}", event);
        return
    }
}
