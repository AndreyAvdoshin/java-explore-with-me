package ru.practicum.ewm.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entityType, long id) {
        super(String.format("Объект %s по id - %s не найден", entityType, id));
    }
}
