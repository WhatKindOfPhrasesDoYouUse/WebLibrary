package com.vyatsu.springLibrary.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "rent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @Column(name = "rented", nullable = false, columnDefinition = "boolean default false")
    private boolean rented;
    @Column(name = "rented_start", nullable = false)
    private LocalDate rentedStart;
    @Column(name = "rented_end", nullable = false)
    private LocalDate rentedEnd;
}
