package com.application.book.library_management.service;

import java.util.List;

import com.application.book.library_management.dto.BookDto;

public interface BookService {
    

    public BookDto saveBook(BookDto bookDto);
    BookDto getBookById(Long bookDto);
    public List<BookDto> getAllBooks();
    public BookDto updateBook(Long bookId, BookDto bookDto);
    public void deleteBook(Long bookId);
    public List<BookDto> searchBooksByTitleOrAuthor(String keyword);

    
}
