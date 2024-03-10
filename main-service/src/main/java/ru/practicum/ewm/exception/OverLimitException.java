package ru.practicum.ewm.exception;

public class OverLimitException extends RuntimeException {
    public OverLimitException(String message) {
        super(message);
    }
}
