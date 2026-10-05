package com.posjetihercegovinu.backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {

    // ID baza dodjeljuje sam , klijent ga ne salje pri kreiranju
    private Long id;

    @NotBlank(message = "Naziv mjesta je obavezan")
    @Size(max = 200, message = "Naziv moze imati najvise 200 znakova")
    private String name;

    // Opis je opcioni (u entitetu je TEXT, pa nema ogranicenja duzine)
    private String description;

    // Klijent salje samo ID kategorije npr. 1-Priroda
    //@NotNull : kategorija je obavezna , mjesto ne smije ostati bez nje
    @NotNull
    private Long categoryId;

    // Ovo polje popunjava server pri vracanju odgovora, da kljent  odmah vidi
    // naziv kategorije , bez dodatnog zahtjeva. Pri slanju ga klijent moze ignorisati
    private String categoryName;

    @Size(max = 300, message = "Adresa moze imati najvise 300 znakova")
    private String address;

    // Geografski sirina moze biti od -90 do 90, a duzina -180 do 180
    // Sprecavaju se besmislene koordinate
    @DecimalMin(value = "-90.0", message = "latitude mora biti izmedju -90 i 90")
    @DecimalMax(value = "90.0", message = "Latitude mora biti izmedju -90 i 90")
    private BigDecimal latitude;

    @DecimalMin(value = "-180.0", message = "Longitude mora biti izmedju -180 i 180")
    @DecimalMax(value = "180.0", message = "Longitude mora biti izmedju -180 i 180")
    private BigDecimal longitude;

    @Size(max = 200, message = "Radno vrijeme moze imati najvise 200 znakova")
    private String openingHours;

    // Cijena ulaznica opciona , ne smije biti negativna
    @DecimalMin(value = "0.0", message = "Cijena ne smije biti negativna")
    private BigDecimal price;

    @Size(max = 500, message = "URL slike moze imati najvise 500 znakova")
    private String imageUrl;














}
