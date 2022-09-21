package com.edu.ulab.app.exception;

public class NotFoundException extends BadRequestException {
    public NotFoundException(String message) {
        super(message);
    }
}
