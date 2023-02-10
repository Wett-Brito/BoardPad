package br.com.boardpadbackend.exceptions;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {
        super("An internal server error has occurred. Please try again later.");
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
