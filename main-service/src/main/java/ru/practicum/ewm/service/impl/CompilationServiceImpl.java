package ru.practicum.ewm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.CompilationMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.EventMapper;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.CompilationService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository repository;
    private final EventRepository eventRepository;

    public CompilationServiceImpl(CompilationRepository repository, EventRepository eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Set<Event> events = new HashSet<>(eventRepository.findAllById(newCompilationDto.getEvents()));

        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
        compilation.setEvents(events);

        compilation = repository.save(compilation);
        log.info("Сохранение подборки событий - {}", compilation);

        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public CompilationDto updateCompilation(UpdateCompilationRequest updateRequest, Long compId) {
        Compilation compilation = returnIfExists(compId);

        compilation.setPinned(updateRequest.isPinned());
        compilation.setTitle(updateRequest.getTitle());

        Set<Event> events;
        if (updateRequest.getEvents() != null) {
            events = new HashSet<>(eventRepository.findAllById(updateRequest.getEvents()));
            compilation.setEvents(events);
        }

        repository.save(compilation);
        log.info("Обновление подборки событий - {}", compilation);

        return CompilationMapper.toCompilationDto(compilation);

    }

    @Override
    public List<CompilationDto> getCompilations(Boolean isPinned, int from, int size) {
        PageRequest page = PageRequest.of(from / size, size);
        List<Compilation> compilations = isPinned != null ?
                repository.findAllByPinnedIs(isPinned, page) : repository.findAll(page).getContent();
        log.info("Возврат списка подборок событий - {}", compilations);

        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        CompilationDto compilationDto = CompilationMapper.toCompilationDto(returnIfExists(compId));
        log.info("Возврат подборки - {}", compilationDto);

        return compilationDto;
    }

    @Override
    public void deleteCompilation(Long compId) {
        checkExisting(compId);
        log.info("Удаление подборки событий по id - {}", compId);

        repository.deleteById(compId);
    }

    private void checkExisting(Long compId) {
         boolean exist = repository.existsById(compId);
         if (!exist) {
             throw new NotFoundException("compilation", compId);
         }
    }

    private Compilation returnIfExists(Long compId) {
        return repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("compilation", compId));
    }
}
