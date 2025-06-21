package com.application.book.library_management.dto;

import java.time.LocalDate;

import com.application.book.library_management.models.BookEntity;

import lombok.Data;

@Data
public class HistoryDto {
    
    private Long historyId;
    private Long studentId;
    private Long bookId;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private String flag;

    private String bookName;  
    private String firstName;

}
