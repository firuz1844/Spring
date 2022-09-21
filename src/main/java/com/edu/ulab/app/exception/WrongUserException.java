package com.edu.ulab.app.exception;

public class WrongUserException extends BadRequestException {
    public WrongUserException(String message) {
        super(message);
    }
}

