package com.example.FirstRestApp.controller;

import com.example.FirstRestApp.model.Person;
import com.example.FirstRestApp.services.PeopleService;
import com.example.FirstRestApp.util.PersonErrorResponse;
import com.example.FirstRestApp.util.PersonNotCreatedException;
import com.example.FirstRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService){
        this.peopleService = peopleService;
    }

    @GetMapping()
    public ResponseEntity<List<Person>> getPeople(){
        List<Person> people = peopleService.findAll();   // Jackson автоматиески конвертирует эти обЪекты в JSON
        return ResponseEntity.ok().body(people);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id){
        Person person = peopleService.findOne(id);
        return ResponseEntity.ok().body(person);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person, BindingResult bindingResult){     // @RequestBody принимает данные от пользователя, конвертируя в json
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new PersonNotCreatedException(errorMessage.toString());
        }
        peopleService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException exception){
        PersonErrorResponse response = new PersonErrorResponse("Такого пользователя не существует", System.currentTimeMillis());
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException exception){
        PersonErrorResponse response = new PersonErrorResponse(exception.getMessage(), System.currentTimeMillis());
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
