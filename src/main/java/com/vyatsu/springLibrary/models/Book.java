package com.vyatsu.springLibrary.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
}
