package com.posjetihercegovinu.backend.repository;

import com.posjetihercegovinu.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    // Spring sam pravi upit iz imena metode: SELECT...WHERE email = ?
    // Optional - rezultat moze postojati ili ne (nema korisnika sa tim emailom)
    Optional<User> findByEmail(String email);

    // Brza provjera da li je email vec zauzet(za registraciju)
    boolean existsByEmail(String email);

}
