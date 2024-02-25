package ru.practicum.ewm.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.server.service.StatService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatController.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"db.name=test"})
class StatControllerTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatService statService;

    private EndpointHitDto endpointHitDto;
    private ViewStatsDto viewStatsDto;

    @BeforeEach
    void setup() {
        endpointHitDto = EndpointHitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.168.0.1")
                .timestamp(LocalDateTime.now())
                .build();

        viewStatsDto = ViewStatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(1L)
                .build();
    }

    @Test
    void shouldCreateHitAndGet201() throws Exception {
        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(endpointHitDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(statService, times(1)).createHit(any());
    }

    @Test
    void shouldGetStats() throws Exception {
        String start = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String end = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<String> uris = List.of(endpointHitDto.getUri());

        when(statService.getStats(any(LocalDateTime.class), any(LocalDateTime.class), any(List.class),
                any(Boolean.class))).thenReturn(List.of(viewStatsDto));

        mvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .param("uris", String.join(",", uris))
                        .param("unique", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].app", is(viewStatsDto.getApp())))
                .andExpect(jsonPath("$.[0].uri", is(viewStatsDto.getUri())))
                .andExpect(jsonPath("$.[0].hits", is(viewStatsDto.getHits().intValue())));
    }
}