package org.blogs.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Entity not found")
public class NotFoundException extends Exception {

    private static final String MESSAGE = "Entity not found";

    public NotFoundException() {
        super(MESSAGE);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
