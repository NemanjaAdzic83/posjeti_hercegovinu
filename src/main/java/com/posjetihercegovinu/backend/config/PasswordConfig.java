package com.posjetihercegovinu.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration - klasa u kojoj definisemo objekte("beanove") koje Spring treba da napravi
@Configuration
public class PasswordConfig {

    // Pravi encoder koji koristi Bcrypt. Svaki hash dobija nasumicnu "so"(salt)
    // pa dvije iste lozinke daju razlicite hash-eve
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
