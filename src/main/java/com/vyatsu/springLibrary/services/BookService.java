package com.vyatsu.springLibrary.services;

import com.vyatsu.springLibrary.models.Book;
import com.vyatsu.springLibrary.models.Rent;
import com.vyatsu.springLibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static antlr.build.ANTLR.root;

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

    public Page<Book> pageAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void update(Long id, String title, String author, int quantity, String publishingHouse,
                       int yearPublishing, String description, int ageLimit, String category) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setQuantity(quantity);
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

    public Page<Book> filterBooks(String title, String category, Pageable pageable) {
        Specification<Book> specification = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (category != null && !category.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category"), category));
        }

        return bookRepository.findAll(specification, pageable);
    }

}
