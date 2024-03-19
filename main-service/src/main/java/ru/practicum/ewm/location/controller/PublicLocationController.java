package ru.practicum.ewm.location.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.service.LocationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/locations")
public class PublicLocationController {

    private final LocationService service;

    public PublicLocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<LocationDto> getLocations(@RequestParam(required = false) String text,
                                          @RequestParam(defaultValue = "0") int from,
                                          @RequestParam(defaultValue = "10") int size) {
        log.info("Публичный запрос всех локаций по тексту - {} начало - {} размером - {}", text, from, size);
        return service.getLocations(text, from, size);
    }
}
