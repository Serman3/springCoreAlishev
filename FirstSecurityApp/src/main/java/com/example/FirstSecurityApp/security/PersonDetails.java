package com.example.FirstSecurityApp.security;

import com.example.FirstSecurityApp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {

    @Autowired
    private final Person person;

    public PersonDetails(Person person){
        this.person = person;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Метод возвращает авторити пользователя (в том числе роли) при авторизации на основе ролей
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole())); // коллекция из одного элемента(т.к роль у пользователя только одна)
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Нужно чтобы получать данные аутентифицированного пользователя
    public Person getPerson(){
        return this.person;
    }
}
