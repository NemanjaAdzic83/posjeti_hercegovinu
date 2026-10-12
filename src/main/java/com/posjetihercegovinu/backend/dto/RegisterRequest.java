package com.posjetihercegovinu.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// Sta klijent salje pri prijavi
@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Ime je obavezno")
    @Size(max = 100, message = "Ime moze imati najvise 100 znakova")
    private String name;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email nije ispravan")
    @Size(max = 150, message = "Email moze imati najvise 150 znakova")
    private String email;

    @NotBlank(message = "Lozinka je obavezna")
    @Size(min = 8, max = 72, message = "Lozinka mora imati izmedju 8 i 72 znaka")
    private String password;



}
