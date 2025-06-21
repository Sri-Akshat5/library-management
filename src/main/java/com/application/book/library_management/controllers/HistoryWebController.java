package com.application.book.library_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.application.book.library_management.dto.BookDto;
import com.application.book.library_management.dto.HistoryDto;
import com.application.book.library_management.dto.StudentDto;
import com.application.book.library_management.service.BookService;
import com.application.book.library_management.service.HistoryService;
import com.application.book.library_management.service.StudentService;
@Controller
@RequestMapping("/history")
public class HistoryWebController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public String viewAllHistory(Model model) {
        model.addAttribute("histories", historyService.getAllHistory());
        return "history/list";
    }

 

    @GetMapping("/borrow")
public String showBorrowForm(Model model) {
    model.addAttribute("students", studentService.getAllStudents());
    model.addAttribute("books", bookService.getAllBooks());
    return "history/borrow";
}

@PostMapping("/borrow")
public String borrowBook(@RequestParam Long studentId,
                         @RequestParam Long bookId,
                         RedirectAttributes redirectAttributes) {
    try {
        historyService.borrowBook(studentId, bookId);
        redirectAttributes.addFlashAttribute("successMessage", "Book borrowed successfully.");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/history";
}



@GetMapping("/return/{id}")
public String returnBook(@PathVariable Long id) {
    // Call your service method to mark the book as returned
    historyService.returnBook(id);
    return "redirect:/history";
}

@GetMapping("/search")
public String searchHistory(@RequestParam("keyword") String keyword, Model model) {
    List<HistoryDto> searchResults = historyService.searchHistoryByName(keyword);
    model.addAttribute("histories", searchResults);
    return "history/list";
}


}
