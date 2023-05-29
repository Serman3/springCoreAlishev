package com.example.SpringCrud.dao;

import com.example.SpringCrud.models.Book;
import com.example.SpringCrud.models.Person;
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
}
