package com.example.SpringDataCrud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "Author should not be empty")
    @Column(name = "author", nullable = false)
    private String author;

    @Min(value = 0, message = "Year should be greater than 0")
    @Column(name = "years", nullable = false)
    private int years;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "date_capture_book")
    private LocalDateTime date;

    @Transient
    private boolean resultOverdue = false;

    public Book(){};

    public Book(int id, String name, String author, int years) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.years = years;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isResultOverdue() {
        return resultOverdue;
    }

    public void setResultOverdue(boolean resultOverdue) {
        this.resultOverdue = resultOverdue;
    }
}
