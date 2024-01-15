package com.vyatsu.springLibrary.services;

import com.vyatsu.springLibrary.models.Book;
import com.vyatsu.springLibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void update(Long id, String title, String author, String publishingHouse,
                       int yearPublishing, String description, int ageLimit, String category) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublishingHouse(publishingHouse);
            book.setYearPublishing(yearPublishing);
            book.setDescription(description);
            book.setAgeLimit(ageLimit);
            book.setCategory(category);
            bookRepository.save(book);
        }
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
