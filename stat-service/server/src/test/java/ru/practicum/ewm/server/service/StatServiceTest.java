package ru.practicum.ewm.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.server.Repository.StatRepository;
import ru.practicum.ewm.server.model.Hit;
import ru.practicum.ewm.server.model.Stats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"db.name=test"})
class StatServiceTest {

    @InjectMocks
    StatServiceImpl statService;

    @Mock
    StatRepository statRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Hit hit;
    private Stats stats;
    private Stats stats2;
    private ViewStatsDto viewStatsDto;
    private ViewStatsDto viewStatsDto2;
    private EndpointHitDto endpointHitDto;


    @BeforeEach
    void setUp() {
        hit = Hit.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.168.0.1")
                .timestamp(LocalDateTime.of(2024, 1, 1, 10, 0))
                .build();

        stats = Stats.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(1L)
                .build();

        stats2 = Stats.builder()
                .app("ewm-main-service")
                .uri("/events/2")
                .hits(1L)
                .build();

        endpointHitDto = EndpointHitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.168.0.1")
                .timestamp(LocalDateTime.of(2024, 1, 1, 10, 0))
                .build();

        viewStatsDto = ViewStatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(1L)
                .build();

        viewStatsDto2 = ViewStatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/2")
                .hits(1L)
                .build();

    }

    @Test
    void shouldCreateHit() {
        when(statRepository.save(hit)).thenReturn(hit);
        statService.createHit(endpointHitDto);
        verify(statRepository, times(1)).save(any(Hit.class));
    }

    @Test
    void shouldGetAllStats() {
        when(statRepository.findAllHits(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(stats, stats2));

        List<ViewStatsDto> statsDtos = statService.getStats(
                LocalDateTime.of(2024, 1, 1, 0, 0).format(FORMATTER),
                LocalDateTime.now().format(FORMATTER), null, false);

        assertEquals(statsDtos, List.of(viewStatsDto, viewStatsDto2));
    }

    @Test
    void shouldGetUniqueStats() {
        when(statRepository.findAllUniqueHits(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(stats, stats2));

        List<ViewStatsDto> statsDtos = statService.getStats(
                LocalDateTime.of(2024, 1, 1, 0, 0).format(FORMATTER),
                LocalDateTime.now().format(FORMATTER), null, true);

        assertEquals(statsDtos, List.of(viewStatsDto, viewStatsDto2));
    }

    @Test
    void shouldGetUniqueStatsByUri() {
        when(statRepository.findAllUniqueHitsByUri(any(LocalDateTime.class), any(LocalDateTime.class), any(List.class)))
                .thenReturn(List.of(stats));

        List<ViewStatsDto> statsDtos = statService.getStats(
                LocalDateTime.of(2024, 1, 1, 0, 0).format(FORMATTER),
                LocalDateTime.now().format(FORMATTER), List.of(stats.getUri()), true);

        assertEquals(statsDtos, List.of(viewStatsDto));

    }

    @Test
    void shouldGetStatsByUri() {
        when(statRepository.findAllUniqueHitsByUri(any(LocalDateTime.class), any(LocalDateTime.class), any(List.class)))
                .thenReturn(List.of(stats2));

        List<ViewStatsDto> statsDtos = statService.getStats(
                LocalDateTime.of(2024, 1, 1, 0, 0).format(FORMATTER),
                LocalDateTime.now().format(FORMATTER), List.of(stats2.getUri()), true);

        assertEquals(statsDtos, List.of(viewStatsDto2));

    }

}