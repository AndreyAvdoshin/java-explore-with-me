package ru.practicum.ewm.exception;

import lombok.Getter;

@Getter
public class IncorrectParameterException extends RuntimeException {

    private String field;

    public IncorrectParameterException(String field, String message) {
        super(message);
        this.field = field;
    }
}
