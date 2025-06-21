package com.application.book.library_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.book.library_management.service.BookService;
import com.application.book.library_management.service.StudentService;
import com.application.book.library_management.service.HistoryService;



@Controller
public class HomeController {

     @Autowired
    private BookService bookService; 

    @Autowired
    private StudentService studentService; 

    @Autowired
    private HistoryService historyService; 

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("bookCount", bookService.getAllBooks().size());
        model.addAttribute("studentCount", studentService.getAllStudents().size());
        model.addAttribute("borrowedCount", historyService.getAllCurrentlyBorrowedBooks().size());
        return "dashboard/home";
    }
}
