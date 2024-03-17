package ru.practicum.ewm.location.model;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;

@UtilityClass
public class LocationMapper {
    public static Location toLocation(NewLocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .radius(locationDto.getRadius())
                .name(locationDto.getName())
                .build();
    }

    public static LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .lat(location.getLat())
                .lon(location.getLon())
                .radius(location.getRadius())
                .name(location.getName())
                .build();
    }

    public static Location toLocation(double lat, double lon) {
        return Location.builder()
                .lat(lat)
                .lon(lon)
                .build();
    }
}
