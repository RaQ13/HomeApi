package pl.homeapp.homeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(Long id) {super("Resource not found with id: " + id);}
    public ResourceNotFoundException(String message) {super(message);}
}
