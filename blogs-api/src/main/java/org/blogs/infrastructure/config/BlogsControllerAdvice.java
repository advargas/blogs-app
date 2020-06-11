package org.blogs.infrastructure.config;

import org.blogs.application.response.ResponseEnvelop;
import org.blogs.infrastructure.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.ws.rs.NotAuthorizedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BlogsControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEnvelop<?> processValidationError(MethodArgumentNotValidException ex) {

        ResponseEnvelop<?> response = new ResponseEnvelop<Object>(false, "");

        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> {
                    String name = field.getField();
                    String errorMessage = field.getDefaultMessage();
                    return name + ": " + errorMessage;
                })
                .collect(Collectors.toList());

        response.setErrors(validationErrors);
        return response;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEnvelop<?> processNotFoundException(NotFoundException ex) {
        ResponseEnvelop<?> response = new ResponseEnvelop<Object>(false, "");
        response.addError(ex.getMessage());
        return response;
    }

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEnvelop<?> processRuntimeException(NotAuthorizedException ex) {
        ResponseEnvelop<?> response = new ResponseEnvelop<Object>(false, "");
        response.addError("Authentication error, please verify username and password");
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEnvelop<?> processRuntimeException(RuntimeException ex) {
        ResponseEnvelop<?> response = new ResponseEnvelop<Object>(false, "");
        response.addError("Internal Server Error");
        return response;
    }
}
