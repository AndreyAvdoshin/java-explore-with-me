package ru.practicum.ewm.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entityType, Long id) {
        super(String.format("Объект %s по id - %s не найден", entityType, id));
    }

    public NotFoundException(String message) {
        super(message);
    }
}
