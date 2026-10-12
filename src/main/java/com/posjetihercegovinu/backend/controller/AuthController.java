package com.posjetihercegovinu.backend.controller;

import com.posjetihercegovinu.backend.dto.AuthResponse;
import com.posjetihercegovinu.backend.dto.LoginRequest;
import com.posjetihercegovinu.backend.dto.RegisterRequest;
import com.posjetihercegovinu.backend.dto.UserDto;
import com.posjetihercegovinu.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST /api/auth/register - registracija. Vraca 201 i podatke o korisniku
    // Bez @Valid validacija se ne pokrece
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }

    // POST /api/auth/login - prijava. Vraca 200 i podatke o korisniku
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }

}
