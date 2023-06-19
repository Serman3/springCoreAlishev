package com.example.FirstSecurityApp.security;

import com.example.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Collections;

// Если логика аутентификации стандартаная (как указана в методе authenticate) то класс не обязательно реализовывать, т.к spring security сам проверит пользователя. Такой класс нужен для своей реализации аутентификации
@Component
public class AuthProviderImpl implements AuthenticationProvider {

    @Autowired
    private final PersonDetailsService personDetailsService;

    public AuthProviderImpl(PersonDetailsService personDetailsService){
        this.personDetailsService = personDetailsService;
    }

    // Метод  принимает на вход Authentication обЪект с credentials с username и password, а возвращает Authentication обЪект с principal c обЪектом PersonDetails и данными о пользователе
    // Spring Security сам вызывает метод uthenticate и сам внедряет параметр Authentication
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails personDetails = personDetailsService.loadUserByUsername(username);
        String password = authentication.getCredentials().toString();

        if(!password.equals(personDetails.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        // Тут Authentication обЪект с principal c паролем и пустым списком прав
        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
