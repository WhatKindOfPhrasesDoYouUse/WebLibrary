package com.vyatsu.springLibrary.services;

import com.vyatsu.springLibrary.models.Review;
import com.vyatsu.springLibrary.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review getById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Review> getByBook(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    public double getAvgRating(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviews.stream()
                .mapToInt(Review::getReviewRating)
                .average()
                .orElse(0.0);
    }
}
