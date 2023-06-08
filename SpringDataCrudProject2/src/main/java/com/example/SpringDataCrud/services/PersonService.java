package com.example.SpringDataCrud.services;

import com.example.SpringDataCrud.models.Person;
import com.example.SpringDataCrud.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }


    public List<Person> index(){
        return personRepository.findAll();
    }

    public Person show(int id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person  person){
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person){
        Person personToBeUpdated = show(id);
        personToBeUpdated.setFio(person.getFio());
        personToBeUpdated.setYearBirthday(person.getYearBirthday());
        personRepository.save(personToBeUpdated);
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    public List<Person> getAllPersonByBookId(int bookId){
        return personRepository.getAllPersonByBookId(bookId);
    }

}
