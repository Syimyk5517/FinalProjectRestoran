package com.example.finalprojectrestoran.exception;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String exceptionClassName;
    private String message;

    public ExceptionResponse(HttpStatus httpStatus, String exceptionClassName, String message) {
        this.httpStatus = httpStatus;
        this.exceptionClassName = exceptionClassName;
        this.message = message;
    }
}
