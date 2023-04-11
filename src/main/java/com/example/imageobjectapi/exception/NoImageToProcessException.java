package com.example.imageobjectapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoImageToProcessException extends RuntimeException {
    public NoImageToProcessException(String message) {
        super(message);
    }
}
