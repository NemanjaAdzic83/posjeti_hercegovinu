package com.posjetihercegovinu.backend.service;

import com.posjetihercegovinu.backend.dto.LoginRequest;
import com.posjetihercegovinu.backend.dto.RegisterRequest;
import com.posjetihercegovinu.backend.dto.UserDto;
import com.posjetihercegovinu.backend.entity.Role;
import com.posjetihercegovinu.backend.entity.User;
import com.posjetihercegovinu.backend.exception.EmailAlreadyUsedException;
import com.posjetihercegovinu.backend.exception.InvalidCredentialsException;
import com.posjetihercegovinu.backend.repository.UserRepository;
import com.posjetihercegovinu.backend.security.JwtService;
import com.posjetihercegovinu.backend.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // registruje novo korisnika- normalizuje email(mala slova bez razmaka)
    // provjerava da nije zauzet, hashira lozinku i sprema korisnika s ulogom USER
    // Uloga je uvijek USER, klijent ne moze sam sebi dati ADMIN
    @Transactional
    public AuthResponse register(RegisterRequest request){
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)){
            throw new EmailAlreadyUsedException("Email je vec registrovan");
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        // Cuvamo rezultat u varijabli "saved"
        User saved =  userRepository.save(user);

        return new AuthResponse(jwtService.generateToken(saved), toDto(saved)); // token + korisnik

    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request){
        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Pogresan email ili lozinka"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Pogresan email ili lozinka");
        }
        return new AuthResponse(jwtService.generateToken(user), toDto(user));
    }


    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole().name());
    }
}
