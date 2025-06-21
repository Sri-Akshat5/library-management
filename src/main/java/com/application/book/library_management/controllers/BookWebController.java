package com.application.book.library_management.controllers;

import com.application.book.library_management.dto.BookDto;
import com.application.book.library_management.dto.StudentDto;
import com.application.book.library_management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookWebController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("book", bookDto);
        return "book/add-form";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute BookDto bookDto) {
        // Set available copies equal to total copies for new books
        bookDto.setAvailableCopies(bookDto.getTotalCopies());
        bookService.saveBook(bookDto);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BookDto bookDto = bookService.getBookById(id);
        model.addAttribute("book", bookDto);
        return "book/edit-form";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute BookDto bookDto) {
        // Preserve the original available copies count

        bookService.updateBook(id, bookDto);
        return "redirect:/books";
    }

    @GetMapping("delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("success", "Book deleted successfully.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam("keyword") String keyword, Model model) {
        List<BookDto> searchResults = bookService.searchBooksByTitleOrAuthor(keyword);
        model.addAttribute("books", searchResults);
        return "book/list";
    }
}
