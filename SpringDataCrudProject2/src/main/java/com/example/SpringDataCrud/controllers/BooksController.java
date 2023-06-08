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

import java.util.*;

@Controller
@RequestMapping("books")
public class BooksController {

    @Autowired
    private PersonService personService;
    @Autowired
    private BookService bookService;

    public BooksController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping
    public String books(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "books_per_page", defaultValue = "20") int itemsPerPage,
                                     @RequestParam(name = "sort_by_year", defaultValue = "false") boolean sortByYear){
        if (sortByYear && page == 0){
            model.addAttribute("books", bookService.allBooks());
            return "books/allBooks";
        }
        model.addAttribute("books", bookService.allBooks(page, itemsPerPage, sortByYear));
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
        bookService.createBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable int id, Model model){
        model.addAttribute("book", bookService.showBook(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}/update")
    public String updateBook(@ModelAttribute @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/books/edit";
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.showBook(id));
        model.addAttribute("people", personService.getAllPersonByBookId(id));
        model.addAttribute("allPeople", personService.index());
        return "books/show";
    }

    @PatchMapping("/{id}")
    public String releaseTheBooks(@PathVariable int id){
        bookService.releaseTheBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add_person")
    public String addBookToPerson(@PathVariable("id") int bookId, @ModelAttribute("person") Person person){
        // ModelAttribute берет из выпадающего списка (формы) выбранный пользоателем id  и кладет в этот пустой обЪект Person
        bookService.addBookToPerson(person.getId(), bookId);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBooks(Model model, @RequestParam(name = "query", required = false) String query){
        List<Book> books = bookService.searchBooks(query).orElse(new ArrayList<>());
        if(query == null || query.equals("")){
            model.addAttribute("searchBooks", new ArrayList<>());
            return "books/search";
        }
        model.addAttribute("searchBooks", books);
        return "books/search";
    }

}
