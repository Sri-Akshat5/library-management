package com.application.book.library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.book.library_management.models.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long>{
    BookEntity findByAuthor(String author);
    BookEntity findByBookName(String bookName);
    BookEntity findByIsbn(Long isbn);
    BookEntity findByBookId(Long bookId);
}
