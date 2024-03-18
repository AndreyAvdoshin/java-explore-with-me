package ru.practicum.ewm.exception;

import lombok.Getter;

@Getter
public class DateTimeViolationException extends RuntimeException {

    private final String field;

    public DateTimeViolationException(String field, String message) {
        super(message);
        this.field = field;
    }

}
