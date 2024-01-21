package com.vyatsu.springLibrary.controllers;

import com.vyatsu.springLibrary.models.Book;
import com.vyatsu.springLibrary.models.Review;
import com.vyatsu.springLibrary.services.BookService;
import com.vyatsu.springLibrary.services.ReviewService;
import com.vyatsu.springLibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ReviewController {
    private final ReviewService reviewService;
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, BookService bookService, UserService userService) {
        this.reviewService = reviewService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/review-by-id/{id}")
    public String reviewById(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("id", id);
        return "review";
    }

    @GetMapping("/review-submit")
    private String reviewSubmit(@RequestParam(value = "id") Long id,
                                @RequestParam(value = "reviewText") String reviewText,
                                @RequestParam(value = "reviewRating") int reviewRating,
                                Principal principal) {
        Review review = new Review();
        Book book = bookService.getById(id);
        review.setReviewText(reviewText);
        review.setReviewRating(reviewRating);
        review.setBook(book);
        review.setUser(userService.getUserByUsername(principal.getName()));
        reviewService.save(review);
        return "redirect:/books/info/" + book.getId();
    }
}
