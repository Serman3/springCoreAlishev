package com.example.SpringCrud.controllers;

import com.example.SpringCrud.dao.BookDao;
import com.example.SpringCrud.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("books")
public class BooksController {

    @Autowired
    private final BookDao bookDao;
    @Autowired
    private final PersonDao personDao;

    public BooksController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String books(Model model){
        model.addAttribute("books", bookDao.allBooks());
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.showBook(id));
        model.addAttribute("people", personDao.peopleWhoTookTheBook(id));
        return "books/show";
    }

    @PatchMapping("/{id}")
    public String releaseTheBooks(@PathVariable int id){
        bookDao.releaseTheBook(id);
        return "redirect:/books";
    }
}
