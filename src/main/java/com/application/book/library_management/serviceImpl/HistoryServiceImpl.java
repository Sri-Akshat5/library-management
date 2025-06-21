package com.application.book.library_management.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.book.library_management.dto.HistoryDto;
import com.application.book.library_management.exception.BookAlreadyBorrowedException;
import com.application.book.library_management.exception.BookAlreadyReturnedException;
import com.application.book.library_management.exception.BookNotAvailableException;
import com.application.book.library_management.exception.BookNotFoundException;
import com.application.book.library_management.exception.BorrowRecordNotFoundException;
import com.application.book.library_management.exception.StudentNotFoundException;
import com.application.book.library_management.interceptor.Loggers;
import com.application.book.library_management.models.BookEntity;
import com.application.book.library_management.models.HistoryEntity;
import com.application.book.library_management.models.StudentEntity;
import com.application.book.library_management.repository.BookRepository;
import com.application.book.library_management.repository.HistoryRepository;
import com.application.book.library_management.repository.StudentRepository;
import com.application.book.library_management.service.HistoryService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Loggers loggers;

    @Autowired
    public HistoryServiceImpl() {
        this.loggers = new Loggers(HistoryServiceImpl.class);
    }

    @Override
    public HistoryDto borrowBook(Long studentId, Long bookId) {
        loggers.info("Attempting to borrow book ID: " + bookId + " by student ID: " + studentId);

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    loggers.error("Book not found with ID: " + bookId);
                    return new BookNotFoundException("Book not found");
                });

        if (book.getAvailableCopies() == 0) {
            loggers.warn("Book not available for borrowing. ID: " + bookId);
            throw new BookNotAvailableException("Book is not available for borrowing.");
        }

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    loggers.error("Student not found with ID: " + studentId);
                    return new StudentNotFoundException("Student not found");
                });

        boolean alreadyBorrowed = historyRepository
                .existsByStudentEntity_StudentIdAndBookEntity_BookIdAndFlag(studentId, bookId, "BORROWED");

        if (alreadyBorrowed) {
            loggers.warn("Duplicate borrow attempt by student ID: " + studentId + " for book ID: " + bookId);
            throw new BookAlreadyBorrowedException("You have already borrowed this book and not returned it.");
        }

        HistoryEntity history = new HistoryEntity();
        history.setBookEntity(book);
        history.setStudentEntity(student);
        history.setIssueDate(LocalDate.now());
        history.setFlag("BORROWED");

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        loggers.debug("Book copy count updated after borrow: " + book.getAvailableCopies());

        HistoryDto saved = modelMapper.map(historyRepository.save(history), HistoryDto.class);
        loggers.info("Borrow recorded successfully for student ID: " + studentId);
        return saved;
    }

    @Override
    public HistoryDto returnBook(Long historyId) {
        loggers.info("Attempting to return book for history ID: " + historyId);

        HistoryEntity history = historyRepository.findById(historyId)
                .orElseThrow(() -> {
                    loggers.error("Borrow history not found with ID: " + historyId);
                    return new BorrowRecordNotFoundException("Borrow history not found");
                });

        if ("RETURNED".equalsIgnoreCase(history.getFlag())) {
            loggers.warn("Book already returned for history ID: " + historyId);
            throw new BookAlreadyReturnedException("Book already returned");
        }

        history.setReturnDate(LocalDate.now());
        history.setFlag("RETURNED");

        BookEntity book = history.getBookEntity();
        Integer currentCopies = book.getAvailableCopies() != null ? book.getAvailableCopies() : 0;
        book.setAvailableCopies(currentCopies + 1);

        bookRepository.save(book);
        HistoryEntity updatedHistory = historyRepository.save(history);

        loggers.info("Book returned successfully for history ID: " + historyId);
        return modelMapper.map(updatedHistory, HistoryDto.class);
    }

    @Override
    public List<HistoryDto> getAllHistory() {
        loggers.info("Fetching all history records");
        List<HistoryDto> result = historyRepository.findAll().stream()
                .map(history -> {
                    HistoryDto dto = modelMapper.map(history, HistoryDto.class);
                    dto.setBookName(history.getBookEntity().getBookName());
                    dto.setFirstName(history.getStudentEntity().getFirstName());
                    return dto;
                }).collect(Collectors.toList());
        loggers.debug("Total history records fetched: " + result.size());
        return result;
    }

    @Override
    public List<HistoryDto> getBorrowHistoryForStudent(Long studentId) {
        loggers.info("Fetching borrow history for student ID: " + studentId);
        List<HistoryDto> result = historyRepository.findByStudentEntity_StudentId(studentId)
                .stream()
                .map(history -> {
                    HistoryDto dto = modelMapper.map(history, HistoryDto.class);
                    dto.setBookName(history.getBookEntity().getBookName());
                    dto.setFirstName(history.getStudentEntity().getFirstName());
                    return dto;
                })
                .collect(Collectors.toList());
        loggers.debug("History records found for student ID " + studentId + ": " + result.size());
        return result;
    }

    @Override
    public List<HistoryDto> getAllCurrentlyBorrowedBooks() {
        loggers.info("Fetching all currently borrowed books");
        List<HistoryDto> result = historyRepository.findAll().stream()
                .filter(history -> "BORROWED".equalsIgnoreCase(history.getFlag()))
                .map(history -> {
                    HistoryDto dto = modelMapper.map(history, HistoryDto.class);
                    dto.setBookName(history.getBookEntity().getBookName());
                    dto.setFirstName(history.getStudentEntity().getFirstName());
                    return dto;
                })
                .collect(Collectors.toList());
        loggers.debug("Currently borrowed books count: " + result.size());
        return result;
    }

    @Override
    public List<HistoryDto> searchHistoryByName(String keyword) {
        loggers.info("Searching history with keyword: " + keyword);
        String lowerKeyword = keyword.toLowerCase();

        List<HistoryDto> result = historyRepository.findAll()
                .stream()
                .filter(history ->
                        history.getStudentEntity().getFirstName().toLowerCase().contains(lowerKeyword) ||
                        history.getBookEntity().getBookName().toLowerCase().contains(lowerKeyword))
                .map(history -> {
                    HistoryDto dto = modelMapper.map(history, HistoryDto.class);
                    dto.setBookName(history.getBookEntity().getBookName());
                    dto.setFirstName(history.getStudentEntity().getFirstName());
                    return dto;
                })
                .collect(Collectors.toList());

        loggers.debug("Search result size for keyword '" + keyword + "': " + result.size());
        return result;
    }
}
