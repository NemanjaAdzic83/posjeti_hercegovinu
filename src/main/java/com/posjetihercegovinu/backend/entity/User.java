package com.posjetihercegovinu.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    // unique - dva korisnia ne mogu imati isti email. Email sluzi kao korisnicko ime
    @Column(nullable = false, unique = true , length = 100)
    private String email;

    // Ovdje ide hash lozinka, nikad prava lozinka
    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    // EnumType.STRING - u bazi pise ADMIN, a ne broj 2, citljivije je i sigurnijeako promjenimo redosljed
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Postavlja se automatski prije prvog spremanja
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

}
