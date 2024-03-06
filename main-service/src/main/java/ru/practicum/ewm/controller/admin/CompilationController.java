package ru.practicum.ewm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;
import ru.practicum.ewm.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
public class CompilationController {

    private final CompilationService service;

    public CompilationController(CompilationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Запрос на создание подборки событий - {}", newCompilationDto);
        return service.createCompilation(newCompilationDto);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@RequestBody @Valid UpdateCompilationRequest updateRequest,
                                            @PathVariable @Positive Long compId) {
        log.info("Запрос на изменение подборки по id - {} - {}", compId, updateRequest);
        return service.updateCompilation(updateRequest, compId);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable @Positive Long compId) {
        log.info("Запрос на удаление события по id - {}", compId);
        service.deleteCompilation(compId);
    }
}
