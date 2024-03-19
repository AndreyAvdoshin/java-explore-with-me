package ru.practicum.ewm.location.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.LocationShortDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.location.model.LocationMapper;
import ru.practicum.ewm.location.repository.LocationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;

    public LocationServiceImpl(LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public LocationDto createLocation(NewLocationDto locationDto) {
        Location location = LocationMapper.toLocation(locationDto);

        log.info("Создание локации - {}", location);
        return LocationMapper.toLocationDto(repository.save(location));
    }

    @Override
    public LocationDto getLocationById(Long locId) {
        Location location = returnIdExists(locId);

        log.info("Получение локации по id - {} - {}", locId, location);
        return LocationMapper.toLocationDto(location);
    }

    @Override
    public List<LocationDto> getLocations(String text, int from, int size) {
        PageRequest page = PageRequest.of(from / size, size);
        List<Location> locations = repository.findAllByNameContainingIgnoreCase(text, page);

        log.info("Получение локаций - {}", locations);
        return locations.stream()
                .map(LocationMapper::toLocationDto)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDto updateLocationById(Long locId, LocationDto locationDto) {
        Location location = returnIdExists(locId);
        updateLocation(location, locationDto);

        log.info("Обновление локации по id - {} - {}", locId, location);
        return LocationMapper.toLocationDto(repository.save(location));
    }

    @Override
    public void deleteLocationById(Long locId) {
        log.info("Удаление локации по id - {}", locId);
        repository.delete(returnIdExists(locId));
    }

    @Override
    public Location createOrReturn(LocationShortDto locationShortDto) {
        return repository.findByLatAndLon(locationShortDto.getLat(), locationShortDto.getLon())
                .orElseGet(() -> repository
                        .save(LocationMapper.toLocation(locationShortDto.getLat(), locationShortDto.getLon())));
    }

    private Location returnIdExists(Long locId) {
        return repository.findById(locId).orElseThrow(() -> new NotFoundException("Location", locId));
    }

    private void updateLocation(Location location, LocationDto locationDto) {
        location.setLat(locationDto.getLat() != null ? locationDto.getLat() : location.getLat());
        location.setLon(locationDto.getLon() != null ? locationDto.getLon() : location.getLon());
        location.setRadius(locationDto.getRadius() != null ? locationDto.getRadius() : location.getRadius());
        location.setName(locationDto.getName() != null ? locationDto.getName() : location.getName());
    }
}
