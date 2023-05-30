package com.example.SpringCrud.dao;

import com.example.SpringCrud.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BookDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> allBooks(){
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int id){
        return jdbcTemplate.query("SELECT * FROM books WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().filter(book -> book.getId() == id).findAny().orElse(null);
    }

    public List<Book> showPeopleBooks(int id){
       return jdbcTemplate.query("SELECT * FROM books WHERE people_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void releaseTheBook(int id){
        jdbcTemplate.update("UPDATE books SET people_id = null WHERE id =?", id);
    }

    public void createBook(Book book){
        jdbcTemplate.update("INSERT INTO books (name, author, years) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYears());
    }

    public void deleteBook(int id){
        jdbcTemplate.update("DELETE FROM books WHERE id =?", id);
    }

    public void updateBook(Book book){
        jdbcTemplate.update("UPDATE books SET name =?, author =?, years =? WHERE id =?", book.getName(), book.getAuthor(), book.getYears(), book.getId());
    }

    public void addBookToPerson(int personId, int bookId){
        jdbcTemplate.update("UPDATE books SET people_id = ? WHERE id =?", personId, bookId);
    }
}
