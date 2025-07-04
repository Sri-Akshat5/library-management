package com.application.book.library_management.restcontroller;

import com.application.book.library_management.dto.BookDto;
import com.application.book.library_management.dto.HistoryDto;
import com.application.book.library_management.dto.StudentDto;
import com.application.book.library_management.service.BookService;
import com.application.book.library_management.service.HistoryService;
import com.application.book.library_management.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryRestController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private BookService bookService;

    
    @GetMapping
    public ResponseEntity<List<HistoryDto>> getAllHistory() {
        return ResponseEntity.ok(historyService.getAllHistory());
    }

    
    @GetMapping("/borrow/form-data")
    public ResponseEntity<?> getBorrowFormData() {
        return ResponseEntity.ok(
            new Object() {
                public final List<StudentDto> students = studentService.getAllStudents();
                public final List<BookDto> books = bookService.getAllBooks();
            }
        );
    }

    
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam Long studentId, @RequestParam Long bookId) {
        try {
            historyService.borrowBook(studentId, bookId);
            return ResponseEntity.ok("Book borrowed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @PutMapping("/return/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        try {
            historyService.returnBook(id);
            return ResponseEntity.ok("Book returned successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @GetMapping("/search")
    public ResponseEntity<List<HistoryDto>> searchHistory(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(historyService.searchHistoryByName(keyword));
    }
}
