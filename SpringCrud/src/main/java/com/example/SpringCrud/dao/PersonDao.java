package com.example.SpringCrud.dao;

import com.example.SpringCrud.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM people WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person  person){
        jdbcTemplate.update("INSERT INTO people (fio, year_birthday) VALUES (?,?)", person.getFio(), person.getYearBirthday());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE people SET fio=?, year_birthday=? WHERE id=?", person.getFio(), person.getYearBirthday(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM people WHERE id=?", id);
    }

    public List<Person> getAllPersonByBookId(int bookId){
        return jdbcTemplate.query("""
                                      SELECT p.*
                                      FROM people p
                                      JOIN books b on b.people_id = p.id
                                      WHERE b.id =?
                                      """, new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class));
    }
}
