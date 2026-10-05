package com.posjetihercegovinu.backend.exception;

// Vlastiti izuzetak. "Baca" se kad se trazi nesto sto ne postoji u bazi
// Nasledjuje RuntimeException pa se ne mora deklaristi u potpisu svake metode
public class ResourceNotFoundException extends RuntimeException{

    // Konstruktor prima poruku i prosledjuje je roditeljskoj klasi
    public ResourceNotFoundException(String message){
        super(message);
    }
}
