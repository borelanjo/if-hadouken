package br.com.borelanjo.infrastructure.validator.exception;

public class FieldInvalidException extends RuntimeException {

    private FieldInvalidException() {
    }

    public FieldInvalidException(String message) {
        super(message);
    }
}
