package ru.practicum.ewm.exception;

public class DateTimeViolationException extends RuntimeException {
    private String field;

    public DateTimeViolationException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
