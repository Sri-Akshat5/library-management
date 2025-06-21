package com.application.book.library_management.exception;

public class StudentHasActiveBorrowingsException extends RuntimeException {
    public StudentHasActiveBorrowingsException(String message) {
        super(message);
    }
}
