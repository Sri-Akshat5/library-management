package com.application.book.library_management.restcontroller;

import com.application.book.library_management.service.BookService;
import com.application.book.library_management.service.HistoryService;
import com.application.book.library_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeRestController {

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private HistoryService historyService;

    @GetMapping("/api/dashboard")
    public Map<String, Integer> getDashboardData() {
        Map<String, Integer> data = new HashMap<>();
        data.put("bookCount", bookService.getAllBooks().size());
        data.put("studentCount", studentService.getAllStudents().size());
        data.put("borrowedCount", historyService.getAllCurrentlyBorrowedBooks().size());
        return data;
    }
}
