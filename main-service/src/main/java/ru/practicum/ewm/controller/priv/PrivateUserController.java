package ru.practicum.ewm.controller.priv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.ParticipationRequestService;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping("/users/{userId}/requests")
public class PrivateUserController {

    private final ParticipationRequestService service;

    public PrivateUserController(ParticipationRequestService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable @Positive Long userId,
                                                 @RequestParam @Positive Long eventId) {
        return service.createRequest(userId, eventId);
    }
}
