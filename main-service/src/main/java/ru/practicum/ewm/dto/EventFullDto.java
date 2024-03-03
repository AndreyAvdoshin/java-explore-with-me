package ru.practicum.ewm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.ewm.model.Location;
import ru.practicum.ewm.model.State;
import java.time.LocalDateTime;


@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class EventFullDto extends EventShortDto {

    private LocalDateTime createdOn;
    private String description;
    private Location location;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;

    @Builder(builderMethodName = "fullDtoBuilder")
    public EventFullDto(String annotation,
                        CategoryDto category,
                        int confirmedRequests,
                        String eventDate,
                        Long id,
                        UserShortDto initiator,
                        boolean paid,
                        String title,
                        int views,
                        LocalDateTime createdOn,
                        String description,
                        Location location,
                        int participantLimit,
                        LocalDateTime publishedOn,
                        boolean requestModeration,
                        State state) {
        super(annotation, category, confirmedRequests, eventDate, id, initiator, paid, title, views);
        this.createdOn = createdOn;
        this.description = description;
        this.location = location;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
    }

}
