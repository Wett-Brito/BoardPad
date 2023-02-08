package br.com.boardpadbackend.exceptions;


public class BadRequestException extends RuntimeException{
    private BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
