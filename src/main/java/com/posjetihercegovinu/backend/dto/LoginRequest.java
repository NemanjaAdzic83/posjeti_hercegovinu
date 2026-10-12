package com.posjetihercegovinu.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Sta klijent salje pri prijavi
@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email je obavezan")
    private String email;

    @NotBlank(message = "Lozinka je obavezna")
    private String password;
}
