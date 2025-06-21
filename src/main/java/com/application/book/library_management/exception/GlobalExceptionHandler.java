package com.application.book.library_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.application.book.library_management.interceptor.Loggers;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Loggers loggers = new Loggers(GlobalExceptionHandler.class);

    private ResponseEntity<Object> buildResponse(Exception ex, HttpStatus status, String message) {
        loggers.error("Exception: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        body.put("details", ex.getMessage());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, "Book not found");
    }

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<Object> handleBookNotAvailableException(BookNotAvailableException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.CONFLICT, "Book not available");
    }

    @ExceptionHandler(InvalidBookOperationException.class)
    public ResponseEntity<Object> handleInvalidBookOperationException(InvalidBookOperationException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST, "Invalid book operation");
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<Object> handleDuplicateIsbnException(DuplicateIsbnException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.CONFLICT, "Duplicate ISBN");
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Object> handleStudentNotFoundException(StudentNotFoundException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, "Student not found");
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmailException(DuplicateEmailException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.CONFLICT, "Duplicate email");
    }

    @ExceptionHandler(StudentHasActiveBorrowingsException.class)
    public ResponseEntity<Object> handleStudentHasActiveBorrowingsException(StudentHasActiveBorrowingsException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.CONFLICT, "Student has active borrowings");
    }

    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ResponseEntity<Object> handleBookAlreadyBorrowedException(BookAlreadyBorrowedException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.CONFLICT, "Book already borrowed");
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<Object> handleBookAlreadyReturnedException(BookAlreadyReturnedException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.CONFLICT, "Book already returned");
    }

    @ExceptionHandler(BorrowRecordNotFoundException.class)
    public ResponseEntity<Object> handleBorrowRecordNotFoundException(BorrowRecordNotFoundException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, "Borrow record not found");
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST, "Invalid input");
    }
}
