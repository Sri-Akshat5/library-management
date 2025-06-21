package com.application.book.library_management.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.book.library_management.dto.BookDto;
import com.application.book.library_management.exception.BookAlreadyBorrowedException;
import com.application.book.library_management.exception.BookNotFoundException;
import com.application.book.library_management.exception.DuplicateIsbnException;
import com.application.book.library_management.exception.InvalidInputException;
import com.application.book.library_management.interceptor.Loggers;
import com.application.book.library_management.models.BookEntity;
import com.application.book.library_management.repository.BookRepository;
import com.application.book.library_management.service.BookService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Loggers loggers;

    @Autowired
    public BookServiceImpl() {
        this.loggers = new Loggers(BookServiceImpl.class);
    }

    @Override
    public BookDto saveBook(BookDto bookDto) {
        loggers.info("Attempting to save book with ISBN: " + bookDto.getIsbn());

        validateBook(bookDto);

        BookEntity bookEntity = modelMapper.map(bookDto, BookEntity.class);
        BookEntity savedBook = bookRepository.save(bookEntity);

        loggers.info("Book saved successfully with ID: " + savedBook.getBookId());
        return modelMapper.map(savedBook, BookDto.class);
    }

    @Override
    public List<BookDto> getAllBooks() {
        loggers.info("Fetching all books from database");

        List<BookDto> books = bookRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());

        loggers.debug("Total books fetched: " + books.size());
        return books;
    }

    @Override
    public BookDto updateBook(Long bookId, BookDto bookDto) {
        loggers.info("Attempting to update book with ID: " + bookId);

        BookEntity existing = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    loggers.error("Book not found with ID: " + bookId);
                    return new BookNotFoundException("Book not found");
                });

        if (!existing.getIsbn().equals(bookDto.getIsbn())) {
            BookEntity isbnConflict = bookRepository.findByIsbn(bookDto.getIsbn());
            if (isbnConflict != null) {
                loggers.error("Duplicate ISBN found during update: " + bookDto.getIsbn());
                throw new DuplicateIsbnException("Book with this ISBN already exists.");
            }
        }

        existing.setBookName(bookDto.getBookName());
        existing.setAuthor(bookDto.getAuthor());
        existing.setIsbn(bookDto.getIsbn());
        existing.setTotalCopies(bookDto.getTotalCopies());
        existing.setAvailableCopies(bookDto.getAvailableCopies());

        BookEntity updated = bookRepository.save(existing);

        loggers.info("Book updated successfully with ID: " + updated.getBookId());
        return modelMapper.map(updated, BookDto.class);
    }

    @Override
    public void deleteBook(Long id) {
        loggers.info("Attempting to delete book with ID: " + id);

        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    loggers.error("Book not found with ID: " + id);
                    return new BookNotFoundException("Book not found");
                });

        if (book.getAvailableCopies().intValue() != book.getTotalCopies().intValue()) {
            loggers.warn("Book has issued copies. Cannot delete. ID: " + id);
            throw new BookAlreadyBorrowedException("Cannot delete a book that still has issued copies.");
        }

        bookRepository.delete(book);
        loggers.info("Book deleted successfully with ID: " + id);
    }

    @Override
    public List<BookDto> searchBooksByTitleOrAuthor(String keyword) {
        loggers.info("Searching books with keyword: " + keyword);

        List<BookDto> results = bookRepository.findAll()
                .stream()
                .filter(book -> book.getBookName().toLowerCase().contains(keyword.toLowerCase())
                        || book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());

        loggers.debug("Books matched with keyword '" + keyword + "': " + results.size());
        return results;
    }

    @Override
    public BookDto getBookById(Long id) {
        loggers.info("Fetching book by ID: " + id);

        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> {
                    loggers.error("Book not found with ID: " + id);
                    return new BookNotFoundException("Book not found with ID: " + id);
                });

        loggers.info("Book found: " + bookEntity.getBookName());
        return modelMapper.map(bookEntity, BookDto.class);
    }

    private void validateBook(BookDto bookDto) {
        loggers.debug("Validating book with ISBN: " + bookDto.getIsbn());

        if (bookDto.getTotalCopies() <= 0) {
            loggers.error("Invalid total copies: " + bookDto.getTotalCopies());
            throw new InvalidInputException("Total copies must be greater than 0");
        }

        if (bookRepository.findByIsbn(bookDto.getIsbn()) != null) {
            loggers.error("Duplicate ISBN during save: " + bookDto.getIsbn());
            throw new DuplicateIsbnException("Book with this ISBN already exists.");
        }
    }
}
