package com.posjetihercegovinu.backend.exception;

import java.time.LocalDateTime;

// "record" je skracen nacin pisanja klase koja samo drzi podatke
// Java sama napravi konstruktor i getter-e za ova polja
// Ovo je JSON koji ce klijent (Postan, android, web) dobiti kad nesto krene po zlu
public record ErrorResponse(
        int status,  // HTTP status broj, npr 404
        String error,  // kratka poruka, npr "Not found"
        String message,  // opis razumljiv covjeku
        LocalDateTime timestamp // vrijeme kada se greska dogodila
) {

}
