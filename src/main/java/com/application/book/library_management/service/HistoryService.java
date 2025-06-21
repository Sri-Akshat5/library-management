package com.application.book.library_management.service;

import java.util.List;

import com.application.book.library_management.dto.HistoryDto;
import com.application.book.library_management.dto.StudentDto;

public interface HistoryService {

    public HistoryDto borrowBook(Long studentId, Long bookId);

    public HistoryDto returnBook(Long historyId);

    public List<HistoryDto> getBorrowHistoryForStudent(Long studentId);

    List<HistoryDto> getAllCurrentlyBorrowedBooks();

    List<HistoryDto> getAllHistory();

    List<HistoryDto> searchHistoryByName(String keyword);



    
}
