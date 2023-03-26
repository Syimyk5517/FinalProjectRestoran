package com.example.finalprojectrestoran.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyException extends RuntimeException{
    public AlreadyException() {
    }

    public AlreadyException(String message) {
        super(message);
    }
}
