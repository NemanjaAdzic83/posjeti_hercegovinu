package com.posjetihercegovinu.backend.controller;

import com.posjetihercegovinu.backend.dto.CategoryDto;
import com.posjetihercegovinu.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController : ova klasa prima HTTP zahtjeve i odgovara u JSON formatu
@RestController
// Svi endpointi u ovoj klasi pocinju sa api/categories
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // Vraca listu svih kategorija
    @GetMapping
    public List<CategoryDto> getAll(){
        return categoryService.getAll();
    }

    // POST dodaje novu kategoriju
    // @RequestBody - Spring uzima JSON zahtjev i pretvori ga u CategoryDto
    // @Valid - pokrece provjere iz DTO (@NotBlank, @Size)
    // @ResponseStatus(CREATED) - odgovor
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto){
        return categoryService.create(categoryDto);
    }
}
