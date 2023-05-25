package com.example.SpringCrud.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
public class Person {
    private int id;
    @NotEmpty(message = "Fio should not be empty")
    @Size(min = 2, max = 30, message = "Fio should be between 2 and 30 characters")
    private String fio;
    @Min(value = 0, message = "Year should be greater than 0")
    private int yearBirthday;

    public Person(){};
    public Person(int id, String fio, int yearBirthday) {
        this.id = id;
        this.fio = fio;
        this.yearBirthday = yearBirthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYearBirthday() {
        return yearBirthday;
    }

    public void setYearBirthday(int yearBirthday) {
        this.yearBirthday = yearBirthday;
    }

}
