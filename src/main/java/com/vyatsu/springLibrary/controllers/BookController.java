package com.vyatsu.springLibrary.controllers;

import com.vyatsu.springLibrary.models.Book;
import com.vyatsu.springLibrary.models.Review;
import com.vyatsu.springLibrary.models.User;
import com.vyatsu.springLibrary.services.BookService;
import com.vyatsu.springLibrary.services.ReviewService;
import com.vyatsu.springLibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final UserService userService;
    private final ReviewService reviewService;

    @Autowired
    public BookController(BookService bookService, UserService userService, ReviewService reviewService) {
        this.bookService = bookService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String home(Model model, Principal principal,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable,
                       @RequestParam(value = "title", required = false) String title,
                       @RequestParam(value = "category", required = false) String category) {
        Page<Book> books;
        books = bookService.filterBooks(title, category, pageable);
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("name", user);
        model.addAttribute("books", books);
        model.addAttribute("book", new Book());
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("currentPage", books.getNumber() + 1);
        if (books.getTotalPages() > 0) {
            List<Integer> pageNumber = IntStream.rangeClosed(1, (int) books.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumber", pageNumber);
        }
        return "home";

        /*List<Book> books = bookService.listAll();
        model.addAttribute("books", books);
        model.addAttribute("book", new Book());
        return "home";*/
    }

    @GetMapping("/add")
    public String add() {
        return "book-add";
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
        List<Review> reviews = reviewService.getByBook(book.getId());
        double avg = reviewService.getAvgRating(book.getId());
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avg", avg);
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
        bookService.update(id, updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getQuantity(), updatedBook.getPublishingHouse(),
                updatedBook.getYearPublishing(), updatedBook.getDescription(),
                updatedBook.getAgeLimit(), updatedBook.getCategory());
        return "redirect:/books/info/" + id;
    }
}
