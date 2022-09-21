package com.edu.ulab.app.exception;

public class WrongBookException extends BadRequestException {
    public WrongBookException(String message) {
        super(message);
    }
}

