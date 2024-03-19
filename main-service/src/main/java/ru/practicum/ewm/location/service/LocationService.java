package ru.practicum.ewm.location.service;

import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.LocationShortDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.model.Location;

import java.util.List;


public interface LocationService {

    LocationDto createLocation(NewLocationDto locationDto);

    Location createOrReturn(LocationShortDto locationShortDto);

    LocationDto getLocationById(Long locId);

    LocationDto updateLocationById(Long locId, LocationDto locationDto);

    void deleteLocationById(Long locId);

    List<LocationDto> getLocations(String text, int from, int size);

}
