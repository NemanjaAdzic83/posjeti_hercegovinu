package com.posjetihercegovinu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Sta server vraca korisniku, bez lozinke
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    // Uloga kao tekst ("USER", "ADMIN"), da je klijentu lako citati
    private String role;

}
