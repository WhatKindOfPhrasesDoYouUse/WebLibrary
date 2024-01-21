package com.vyatsu.springLibrary.services;

import com.vyatsu.springLibrary.models.Book;
import com.vyatsu.springLibrary.models.Rent;
import com.vyatsu.springLibrary.models.User;
import com.vyatsu.springLibrary.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RentService {
    private final RentRepository rentRepository;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public RentService(RentRepository rentRepository, UserService userService, BookService bookService) {
        this.rentRepository = rentRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public List<Rent> listAll(Principal principal) {
        return rentRepository.findListByUserUsername(principal.getName());
    }

    public Page<Rent> pageAll(Pageable pageable, Principal principal) {
        return rentRepository.findPageByUserUsername(principal.getName(), pageable);
    }
    @Transactional
    public void addBookToRent(Long bookId, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Book book = bookService.getById(bookId);
        if (rentRepository.existsByUserAndBookAndRentedIsTrue(user, book)) {
            return;
        }
        if (user != null && book != null && book.getQuantity() > 0) {
            Rent rent = new Rent();
            rent.setUser(user);
            rent.setBook(book);
            rent.setRentedStart(LocalDate.now());
            rent.setRentedEnd(LocalDate.now().plusDays(7));
            rent.setRented(true);
            rentRepository.save(rent);
            book.setQuantity(book.getQuantity() - 1);
            bookService.save(book);
            userService.save(user);
        }
    }

    @Transactional
    public void returnBook(Long bookId, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Book book = bookService.getById(bookId);
        Rent rentedBook = rentRepository.findByUserAndBookAndRentedIsTrue(user, book);

        if (rentedBook != null) {
            rentedBook.setRented(false);
            rentRepository.save(rentedBook);
            removeFromRent(user.getId(), bookId);
            book.setQuantity(book.getQuantity() + 1);
            bookService.save(book);
        }
    }

    /*@Transactional
    public void writeReview(Long rentId, String review, int rating, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        if (user != null) {
            Rent rent = rentRepository.findByIdAndUser(rentId, user);
            if (rent != null && rent.isRented()) {
                rent.setReview(review);
                rent.setRating(rating);
                rentRepository.save(rent);
            }
        }
    }*/

    public Rent getRentById(Long id) {
        return rentRepository.findById(id).orElse(null);
    }

    public void removeFromRent(Long userId, Long bookId) {
        rentRepository.deleteByUserIdAndBookId(userId, bookId);
    }
}

