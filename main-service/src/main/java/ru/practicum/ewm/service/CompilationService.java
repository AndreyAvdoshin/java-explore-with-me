package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    CompilationDto updateCompilation(UpdateCompilationRequest updateRequest, Long compId);

    void deleteCompilation(Long compId);

    List<CompilationDto> getCompilations(Boolean isPinned, int from, int size);

    CompilationDto getCompilation(Long compId);
}
