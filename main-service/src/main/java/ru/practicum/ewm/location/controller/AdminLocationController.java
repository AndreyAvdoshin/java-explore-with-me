package ru.practicum.ewm.location.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.service.LocationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/locations")
public class AdminLocationController {

    private final LocationService service;

    public AdminLocationController(LocationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@RequestBody @Valid NewLocationDto locationDto) {
        log.info("Запрос на создание локации - {}", locationDto);
        return service.createLocation(locationDto);
    }

    @GetMapping("/{locId}")
    public LocationDto getLocationById(@PathVariable @Positive Long locId) {
        log.info("Запрос на получение локации по id - {}", locId);
        return service.getLocationById(locId);
    }

    @PatchMapping("/{locId}")
    public LocationDto updateLocation(@RequestBody LocationDto locationDto,
                                      @PathVariable @Positive Long locId) {
        log.info("Запрос на обновление локации по id - {} - {}", locId, locationDto);
        return service.updateLocationById(locId, locationDto);
    }

    @DeleteMapping("/{locId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocationById(@PathVariable Long locId) {
        log.info("Запрос на удаление локаци по id - {}", locId);
        service.deleteLocationById(locId);
    }
}
