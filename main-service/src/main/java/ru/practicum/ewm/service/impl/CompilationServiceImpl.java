package ru.practicum.ewm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.service.CompilationService;

@Slf4j
@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository repository;

    public CompilationServiceImpl(CompilationRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }
}
