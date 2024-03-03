package ru.practicum.ewm.model;

import ru.practicum.ewm.dto.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .createdOn(LocalDateTime.now())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .location(newEventDto.getLocation())
                .paid(newEventDto.isPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .title(newEventDto.getTitle())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event, UserShortDto userShortDto, CategoryDto categoryDto) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(FORMATTER))
                .paid(event.isPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event, UserShortDto userShortDto, CategoryDto categoryDto) {
        return EventFullDto.fullDtoBuilder()
                .annotation(event.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(FORMATTER))
                .id(event.getId())
                .initiator(userShortDto)
                .paid(event.isPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .location(event.getLocation())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.isRequestModeration())
                .title(event.getTitle())
                .build();
    }
}
