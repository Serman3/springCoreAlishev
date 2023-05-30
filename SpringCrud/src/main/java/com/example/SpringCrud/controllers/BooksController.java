package com.example.SpringCrud.controllers;

import com.example.SpringCrud.dao.BookDao;
import com.example.SpringCrud.dao.PersonDao;
import com.example.SpringCrud.models.Book;
import com.example.SpringCrud.models.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/new")
    public String newBook(@ModelAttribute Book book){  // ModelAttribute  создант новый объект Book
        // model.addAttribute("book", new Book());
        return "/books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/books/new";
        }
        bookDao.createBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable int id, Model model){
        model.addAttribute("book", bookDao.showBook(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}/update")
    public String updateBook(@ModelAttribute @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/books/edit";
        }
        bookDao.updateBook(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@PathVariable int id){
        bookDao.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDao.showBook(id));
        model.addAttribute("people", personDao.getAllPersonByBookId(id));
        model.addAttribute("allPeople", personDao.index());
        return "books/show";
    }

    @PatchMapping("/{id}")
    public String releaseTheBooks(@PathVariable int id){
        bookDao.releaseTheBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add_person")
    public String addBookToPerson(@PathVariable("id") int bookId, @ModelAttribute("person") Person person){
        // ModelAttribute берет из выпадающего списка (формы) выбранный пользоателем id  и кладет в этот пустой обЪект Person
        bookDao.addBookToPerson(person.getId(), bookId);
        return "redirect:/books";
    }
}
