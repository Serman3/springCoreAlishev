package com.example.FirstSecurityApp.utill;

import com.example.FirstSecurityApp.models.Person;
import com.example.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService){
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        try{
            personDetailsService.loadUserByUsername(person.getUsername());
        }catch (UsernameNotFoundException ignored){
            return; // все ok, пользователя с таким именем не нет
        }

        errors.rejectValue("username", "", "Пользоваетль с таким именем уже существует");
    }
}
