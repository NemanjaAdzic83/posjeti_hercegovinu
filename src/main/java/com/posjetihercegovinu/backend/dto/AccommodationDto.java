package com.posjetihercegovinu.backend.dto;

import com.posjetihercegovinu.backend.entity.AccommodationType;
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
public class AccommodationDto {

    private Long id;

    @NotBlank(message = "Naziv je obavezan")
    @Size(max = 200, message = "Naziv moze imati najvise  200 znakova")
    private String name;

    @NotNull(message = "Tip smjestaja je bavezan")
    private AccommodationType type;

    private String description;

    @Size(max = 300, message = "Adresa moze imati najvise 300 znakova")
    private String address;

    @DecimalMin(value = "-90.0", message = "Latitude mora biti izmedju -90 i 90")
    @DecimalMax(value = "90.0" , message = "Latitude mora biti izmedju -90 i 90")
    private BigDecimal latitude;

    @DecimalMin(value = "-180", message = "Longitude mora biti izmedju -180 i 180")
    @DecimalMax(value = "180", message = "Longitude mora biti izmedju -180 i 180")
    private BigDecimal longitude;

    @DecimalMin(value = "0.0", message = "Cijena ne smije biti negativna")
    private BigDecimal priceFrom;

    @Size(max = 50, message = "Phone moze imati najvise 50 znakova")
    private String phone;

    @Size(max = 300, message = "Website moze imati najvise 300 znakova")
    private String website;

    @Size(max = 500, message = "URL slike moze imati najc=vise 500 znakova")
    private String imageUrl;



















}
