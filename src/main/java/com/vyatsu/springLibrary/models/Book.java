package com.vyatsu.springLibrary.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "publishing_house", nullable = false)
    private String publishingHouse;
    @Column(name = "year_publishing", nullable = false)
    private int yearPublishing;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "age_limit", nullable = false)
    private int ageLimit;
    @Column(name = "category", nullable = false)
    private String category;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rent> rents;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;
}