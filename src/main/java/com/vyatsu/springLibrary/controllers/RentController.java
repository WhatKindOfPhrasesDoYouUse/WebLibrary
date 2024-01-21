package com.vyatsu.springLibrary.controllers;

import com.vyatsu.springLibrary.models.Rent;
import com.vyatsu.springLibrary.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/rents")
    public String rents(Principal principal, Model model) {
        List<Rent> rents = rentService.listAll(principal);
        model.addAttribute("rents", rents);
        return "rents";
    }

    @GetMapping("/books/rent-book/{id}")
    public String rentBook(@PathVariable(value = "id") Long id, Principal principal) {
        rentService.addBookToRent(id, principal);
        return "redirect:/rents";
    }

    @GetMapping("/rents/return-book/{id}")
    public String returnBook(@PathVariable(value = "id") Long bookId, Principal principal) {
        rentService.returnBook(bookId, principal);
        return "redirect:/books";
    }
}
