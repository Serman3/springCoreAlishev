package com.example.SpringDataCrud.controllers;

import com.example.SpringDataCrud.models.Book;
import com.example.SpringDataCrud.models.Person;
import com.example.SpringDataCrud.services.BookService;
import com.example.SpringDataCrud.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    private PersonService personService;
    @Autowired
    private BookService bookService;

    public PeopleController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personService.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        List<Book> books = bookService.showPeopleBooks(id);
        bookService.checkDateBook(books);
        model.addAttribute("person", personService.show(id));
        model.addAttribute("books", books);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute Person person){   // ModelAttribute  создант новый объект персон
       // model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/people";
    }
}
