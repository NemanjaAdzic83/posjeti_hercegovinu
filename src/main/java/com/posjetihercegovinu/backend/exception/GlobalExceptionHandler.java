package com.posjetihercegovinu.backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


// @RestControllerAdvice - cuvar za sve kontrolere. Kad bilo koji kontroler baci izuzetak
// Spring trazi metodu u ovoj klasi koja ga zna obraditi i raca njen odgovor klijentu
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Hvata ResourceNotFoundException -> odgovor 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException e) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // Hvata greske validacije (@NotBlank, @NotNull, @Size ...) kad je @Valid ukjlucen
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        // Sakupjamo sve poruke gresaka u jedan tekst npr "name: Naziv je obavezan; categoryId: Kategorija je obavezna
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException e){
        ErrorResponse body = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                "Podatak vec postoji ili je povezan sa drugim podacima",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // Hvata sve ostale izuzetke
    // Bez ovoga klijent dobije ruzan odgovor bez objasnjenja
    public ResponseEntity<ErrorResponse> handleGeneral(Exception e){
        e.printStackTrace();
        ErrorResponse body = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Doslo je do greske na serveru",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }



}
