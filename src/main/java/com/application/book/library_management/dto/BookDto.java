package com.application.book.library_management.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BookDto {
    
    private Long bookId;
    private Long isbn;
    private String bookName;
    private String author;
    private Integer totalCopies;
    private Integer availableCopies;
    private Long studentId;

}
