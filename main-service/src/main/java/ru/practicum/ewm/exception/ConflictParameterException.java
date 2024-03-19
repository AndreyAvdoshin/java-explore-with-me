package ru.practicum.ewm.exception;

import lombok.Getter;

@Getter
public class ConflictParameterException extends RuntimeException {

    private final String field;

    public ConflictParameterException(String field, String message) {
        super(message);
        this.field = field;
    }
}
