package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorResponse {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(final MethodArgumentNotValidException e) {
        log.error("Вызвана ошибка валидации - {}", e.getMessage());
//        List<String> errors = new ArrayList<>();
//        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
//            errors.add(stackTraceElement.toString());
//        }
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Неккоректные параметры запроса")
                .message(e.getFieldError().getField() + " " + e.getFieldError().getDefaultMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError dataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.error("Вызвана ошибка уникальности - {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .reason("Нарушение уникальности")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundException(final RuntimeException e) {
        log.error("Вызвана ошибка Значение не найдено - {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .reason("Запращиваемый объект не найден")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ConflictParameterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflictParameterException(final ConflictParameterException e) {
        log.error("Вызвана ошибка валидации поля " + e.getField() + " - {}", e.getMessage());

        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .reason("Некорректное значение поля при запросе")
                .message("Поле: " + e.getField() + " " + e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(OverLimitException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError overLimitException(final OverLimitException e) {
        log.error("Достигнут лимит участников по заявке");

        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .reason("Достигнут лимит участников по заявке")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

//    @ExceptionHandler(IncorrectParameterException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiError incorrectParameterException(IncorrectParameterException e) {
//        log.error("Вызвана ошибка некорректного запроса - {}", e.getMessage());
//        return ApiError.builder()
//                .status(HttpStatus.BAD_REQUEST)
//                .reason("Некорректные данные запроса")
//                .message(e.getMessage())
//                .timestamp(LocalDateTime.now())
//                .build();
//    }
}
