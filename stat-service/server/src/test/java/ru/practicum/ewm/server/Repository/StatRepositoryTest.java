package ru.practicum.ewm.server.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.ewm.server.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class StatRepositoryTest {

    @Autowired
    private StatRepository repository;

    private Hit hit;
    private Hit hit2;

    @BeforeEach
    void setup() {
        hit = Hit.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.168.0.1")
                .timestamp(LocalDateTime.now())
                .build();

        hit2 = Hit.builder()
                .app("ewm-main-service")
                .uri("/events/2")
                .ip("192.168.0.1")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetStatsByUrl() {
        repository.save(hit);
        repository.save(hit2);
        assertEquals(2, repository.findAllHitsByUri(LocalDateTime.now().minusMinutes(1),
                LocalDateTime.now().plusMinutes(5), List.of(hit.getUri(), hit2.getUri())).size());
    }

    @Test
    void shouldGetStatsByUrlUnique() {
        hit2.setIp("192.168.0.2");
        hit2.setUri("/events/1");
        repository.save(hit);
        repository.save(hit2);
        assertEquals(1, repository.findAllUniqueHitsByUri(LocalDateTime.now().minusMinutes(1),
                LocalDateTime.now().plusMinutes(5), List.of(hit.getUri())).size());
    }

    @Test
    void shouldGetUniqueStats() {
        repository.save(hit);
        repository.save(hit2);
        assertEquals(2, repository.findAllUniqueHits(LocalDateTime.now().minusMinutes(1),
                LocalDateTime.now().plusMinutes(5)).size());
    }

    @Test
    void shouldGetAllStats() {
        repository.save(hit);
        repository.save(hit2);
        assertEquals(2, repository.findAllHits(LocalDateTime.now().minusMinutes(1),
                LocalDateTime.now().plusMinutes(5)).size());
    }

}