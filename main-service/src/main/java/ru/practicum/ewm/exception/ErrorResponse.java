package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorResponse {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(final MethodArgumentNotValidException e) {
        log.error("Вызвана ошибка валидации - {}", e.getLocalizedMessage());

        return ApiError.builder()
                .errors(List.of(e.getStackTrace()))
                .status(HttpStatus.BAD_REQUEST)
                .reason("Неккоректные параметры запроса")
                .message(e.getFieldError().getField() + " " + e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({DateTimeViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError dateTimeViolationException(final DateTimeViolationException e) {
        log.error("Вызвана ошибка валидации - {}", e.getLocalizedMessage());

        return ApiError.builder()
                .errors(List.of(e.getStackTrace()))
                .status(HttpStatus.BAD_REQUEST)
                .reason("Неккоректные параметры запроса")
                .message(e.getField() + " " + e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError missingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.error("Вызвана ошибка валидации - {}", e.getLocalizedMessage());

        return ApiError.builder()
                .errors(List.of(e.getStackTrace()))
                .status(HttpStatus.BAD_REQUEST)
                .reason("Неккоректные параметры запроса")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError dataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.error("Вызвана ошибка уникальности - {}", e.getLocalizedMessage());

        return ApiError.builder()
                .errors(List.of(e.getStackTrace()))
                .status(HttpStatus.CONFLICT)
                .reason("Нарушение уникальности")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundException(final NotFoundException e) {
        log.error("Вызвана ошибка Значение не найдено - {}", e.getLocalizedMessage());

        return ApiError.builder()
                .errors(List.of(e.getStackTrace()))
                .status(HttpStatus.NOT_FOUND)
                .reason("Запращиваемый объект не найден")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ConflictParameterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflictParameterException(final ConflictParameterException e) {
        log.error("Вызвана ошибка валидации поля " + e.getField() + " - {}", e.getLocalizedMessage());

        return ApiError.builder()
                .errors(List.of(e.getStackTrace()))
                .status(HttpStatus.CONFLICT)
                .reason("Некорректное значение поля при запросе")
                .message("Поле: " + e.getField() + " " + e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(OverLimitException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError overLimitException(final OverLimitException e) {
        log.error("Достигнут лимит участников по заявке");

        return ApiError.builder()
                .errors(List.of(e.getStackTrace()))
                .status(HttpStatus.CONFLICT)
                .reason("Достигнут лимит участников по заявке")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError incorrectParameterException(final Throwable e) {
        log.error("Вызвана ошибка некорректного запроса - {}", e.getLocalizedMessage());
        return ApiError.builder()
                .errors(List.of(e.getStackTrace()))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .reason("Некорректные данные запроса")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
