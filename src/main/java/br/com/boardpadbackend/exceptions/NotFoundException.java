package br.com.boardpadbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}