package com.vyatsu.springLibrary.controllers;

import com.vyatsu.springLibrary.models.Book;
import com.vyatsu.springLibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String home(Model model) {
        List<Book> books = bookService.listAll();
        model.addAttribute("books", books);
        model.addAttribute("book", new Book());
        return "home";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute(value = "book") Book newBook) {
        bookService.save(newBook);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/info/{id}")
    public String info(@PathVariable(value = "id") Long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "book-info";
    }

    @ModelAttribute("ages")
    public List<Integer> getAges() {
        return Arrays.asList(0, 6, 12, 16, 18);
    }

    @ModelAttribute("categories")
    public List<String> getCategories() {
        return Arrays.asList("Техническая литература", "Хобби", "Домашнее хозяйство", "Художественная");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") Long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "book-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") Long id, @ModelAttribute Book updatedBook) {
        bookService.update(id, updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getPublishingHouse(),
                updatedBook.getYearPublishing(), updatedBook.getDescription(),
                updatedBook.getAgeLimit(), updatedBook.getCategory());
        return "redirect:/books/info/" + id;
    }
}
