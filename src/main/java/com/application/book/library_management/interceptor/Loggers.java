package com.application.book.library_management.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class Loggers implements HandlerInterceptor {
    private final Logger logger;

    public Loggers(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    public void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    public void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    public void error(String message, Object... args) {
        logger.error(message, args);
    }

    public void trace(String message, Object... args) {
        logger.trace(message, args);
    }

    public void logBookOperation(String operation, Long bookId, String additionalInfo) {
        logger.info("[BOOK {}] ID: {} - {}", operation, bookId, additionalInfo);
    }

    public void logStudentOperation(String operation, Long studentId, String additionalInfo) {
        logger.info("[STUDENT {}] ID: {} - {}", operation, studentId, additionalInfo);
    }

    public void logBorrowOperation(String operation, Long studentId, Long bookId, String status) {
        logger.info("[BORROW {}] StudentID: {} BookID: {} - Status: {}", 
                   operation, studentId, bookId, status);
    }

    public void logReturnOperation(Long historyId, Long bookId, String status) {
        logger.info("[RETURN] HistoryID: {} BookID: {} - Status: {}", 
                   historyId, bookId, status);
    }

    public void logException(String context, Exception e) {
        logger.error("[ERROR] Context: {} - Message: {}", context, e.getMessage(), e);
    }

    public void logValidationFailure(String entityType, String validationMessage) {
        logger.warn("[VALIDATION FAILED] {} - {}", entityType, validationMessage);
    }
}