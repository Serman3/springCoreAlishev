package com.example.SpringDataCrud.services;

import com.example.SpringDataCrud.models.Book;
import com.example.SpringDataCrud.models.Person;
import com.example.SpringDataCrud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PersonService personService;

    public BookService(BookRepository bookRepository, PersonService personService){
        this.bookRepository = bookRepository;
        this.personService = personService;
    }

    public Page<Book> allBooks(int page, int itemsPerPage, boolean sortByYears){
        if (sortByYears){
            return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("years")));
        }
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage));
    }

    public List<Book> allBooks(){
        return bookRepository.findAll(Sort.by("years"));
    }

    public Book showBook(int id){
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> showPeopleBooks(int id){
        return bookRepository.findAllByPeopleId(id);
      //  return jdbcTemplate.query("SELECT * FROM books WHERE people_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    @Transactional
    public void releaseTheBook(int id){
        Book book = showBook(id);
        book.setPerson(null);
        bookRepository.save(book);
        //jdbcTemplate.update("UPDATE books SET people_id = null WHERE id =?", id);
    }

    @Transactional
    public void createBook(Book book){
        bookRepository.save(book);
        //jdbcTemplate.update("INSERT INTO books (name, author, years) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYears());
    }

    @Transactional
    public void deleteBook(int id){
        bookRepository.deleteById(id);
        //jdbcTemplate.update("DELETE FROM books WHERE id =?", id);
    }

    @Transactional
    public void updateBook(Book book){
        Book bookToBeUpdated = showBook(book.getId());
        bookToBeUpdated.setName(book.getName());
        bookToBeUpdated.setAuthor(book.getAuthor());
        bookToBeUpdated.setYears(book.getYears());
        bookRepository.save(bookToBeUpdated);
        //jdbcTemplate.update("UPDATE books SET name =?, author =?, years =? WHERE id =?", book.getName(), book.getAuthor(), book.getYears(), book.getId());
    }

    @Transactional
    public void addBookToPerson(int personId, int bookId){
        Book bookToBeUpdated = showBook(bookId);
        Person person = personService.show(personId);
        bookToBeUpdated.setPerson(person);
        bookToBeUpdated.setResultOverdue(false);
        bookToBeUpdated.setDate(LocalDateTime.now());
        bookRepository.save(bookToBeUpdated);
        //jdbcTemplate.update("UPDATE books SET people_id = ? WHERE id =?", personId, bookId);
    }

    public Optional<List<Book>> searchBooks(String searchResponse){
        return bookRepository.searchBooks(searchResponse);
    }

    public void checkDateBook(List<Book> books){
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime bookDate = null;
        for(Book book : books){
            bookDate = book.getDate();
            Duration duration = Duration.between(bookDate, currentDate);
            if(duration.toDays() > 10){
                book.setResultOverdue(true);
            }
        }
    }

}
