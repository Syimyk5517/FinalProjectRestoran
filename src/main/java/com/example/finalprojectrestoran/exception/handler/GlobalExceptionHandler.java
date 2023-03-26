package com.example.finalprojectrestoran.exception.handler;

import com.example.finalprojectrestoran.exception.BadCredentialException;
import com.example.finalprojectrestoran.exception.BadRequestException;
import com.example.finalprojectrestoran.exception.ExceptionResponse;
import com.example.finalprojectrestoran.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.awt.*;
import java.rmi.AlreadyBoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FontFormatException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ExceptionResponse handlerNotFoundException(NotFoundException notFoundException){
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                notFoundException.getClass().getSimpleName(),
                notFoundException.getMessage());
    }
    @ExceptionHandler(AlreadyBoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handlerNotFoundException(AlreadyBoundException alreadyBoundException){
        return new ExceptionResponse(
                HttpStatus.CONFLICT,
                alreadyBoundException.getClass().getSimpleName(),
               alreadyBoundException.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerBadRequestException(BadRequestException badRequestException){
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                badRequestException.getClass().getSimpleName(),
               badRequestException.getMessage());
    }
    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handlerBadCredentialException(BadCredentialException badCredentialException){
        return new ExceptionResponse(
                HttpStatus.FORBIDDEN,
               badCredentialException.getClass().getSimpleName(),
               badCredentialException.getMessage());
    }
}
