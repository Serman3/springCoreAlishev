package com.example.FirstSecurityApp.config;

import com.example.FirstSecurityApp.security.AuthProviderImpl;
import com.example.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   /* @Autowired
    private final AuthProviderImpl authProvider;

    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }

     // Настраивает аутентификацию
    protected void configure(AuthenticationManagerBuilder auth){
      auth.authenticationProvider(authProvider);
    }*/

    @Autowired
    private PersonDetailsService personDetailsService;

    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
            http.csrf().disable()       // отключили csrf токен
                    .authorizeRequests()
                    .antMatchers("/auth/login", "/error").permitAll() // на эти страницы могут зайти все
                    .anyRequest().authenticated()           // на остальные могут только аутентифицированные
                    .and()
                    .formLogin().loginPage("/auth/login") // наша форма с логином
                    .loginProcessingUrl("/process_login")    // spring security сам обработает данные нашей формы login
                    .defaultSuccessUrl("/hello", true) // после успешной аутентификации перенаправляет на эту страницу
                    .failureUrl("/auth/login?error");     // после не успешной аутентификации перенаправляет на эту страницу
    }

    // Настраивает аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
