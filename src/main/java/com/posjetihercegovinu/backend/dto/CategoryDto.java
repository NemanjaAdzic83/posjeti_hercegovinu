package com.posjetihercegovinu.backend.dto;

// DTO (Data Transfer Object) - objekat koji putuje izmedju klijenta (Androi/Web) i servera
// Razlog zasto ne vracamo direktno entitet : kontrolisemo tacno sta se vidi u JSON-u
// i izbjegavamo probleme s vezama izmdju tabela

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor  // prazan konstruktor (potreban za pretvaranje JSON-a u objekat)
@AllArgsConstructor // konstruktor sa svim poljima : new Category(id,name,description)
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Naziv kategorije je obavezan")
    @Size(max = 100, message = "Naziv moze imati najvise 100 znakova")
    private String name;

    @Size(max = 500, message = "Opis moze imati najvise 500 znakova")
    private String description;
}
