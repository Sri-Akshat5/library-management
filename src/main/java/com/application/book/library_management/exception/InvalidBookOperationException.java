package com.application.book.library_management.exception;

public class InvalidBookOperationException extends RuntimeException {
    public InvalidBookOperationException(String message) {
        super(message);
    }
}