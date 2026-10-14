package com.posjetihercegovinu.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequest {

    @jakarta.validation.constraints.NotNull(message = "Ocjena je obavezna")
    @Min(value = 1, message = "Ocjena mora biti izmedju 1 i 5")
    @Max(value = 5, message = "Ocjena mora biti izmedju 1 i 5")
    private Integer rating;

    @Size(max = 1000, message = "Komentar moze imati najvise 1000 znakova")
    private String comment;

}
