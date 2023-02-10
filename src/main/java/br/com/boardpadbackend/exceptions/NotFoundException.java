package br.com.boardpadbackend.exceptions;

public class NotFoundException extends RuntimeException {
    private NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}