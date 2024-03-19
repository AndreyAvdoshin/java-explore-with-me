package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.model.Event;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAllByInitiatorId(Long userId, PageRequest page);

    @Query(value = "SELECT e.* FROM events e JOIN locations l " +
            "ON e.location_id = l.id " +
            "WHERE e.state = 'PUBLISHED' AND distance(l.latitude, l.longitude, ?1, ?2) < ?3", nativeQuery = true)
    List<Event> findAllEventsByLocationAndRadius(double lat, double lon, int radius);

    @Query(value = "SELECT e.* FROM events e JOIN locations l " +
            "ON e.location_id = l.id " +
            "WHERE e.state = 'PUBLISHED' " +
            "AND (distance(l.latitude, l.longitude, ?1, ?2) < l.radius or " +
            "(l.latitude = ?1 and l.longitude = ?2))", nativeQuery = true)
    List<Event> findAllEventsByLocation(double lat, double lon);
}
