package com.vyatsu.springLibrary.repositories;

import com.vyatsu.springLibrary.models.Book;
import com.vyatsu.springLibrary.models.Rent;
import com.vyatsu.springLibrary.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    Page<Rent> findPageByUserUsername(String name, Pageable pageable);

    List<Rent> findListByUserUsername(String name);

    Rent findByUserAndBookAndRentedIsTrue(User user, Book book);

    @Transactional
    void deleteByUserAndBookAndRentedIsTrue(User user, Book book);

    Rent findByIdAndUser(Long id, User user);

    Rent getRentById(Long id);

    void deleteByUserIdAndBookId(Long userId, Long bookId);

    boolean existsByUserAndBookAndRentedIsTrue(User user, Book book);

}
