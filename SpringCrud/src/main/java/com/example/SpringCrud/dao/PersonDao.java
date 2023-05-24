package com.example.SpringCrud.dao;

import com.example.SpringCrud.models.Person;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {

    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Bob", 24, "bob@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Max", 52, "max@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Alex", 31, "alex@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Anna", 28, "anna@yahoo.com"));
        people.add(new Person(++PEOPLE_COUNT, "Katy", 45, "katy@mail.ru"));
    }


    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person  person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person){
        // Обновляем имя
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setEmail(person.getEmail());
    }

    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }
}
