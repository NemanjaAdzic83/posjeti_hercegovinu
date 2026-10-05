package com.posjetihercegovinu.backend.service;

import com.posjetihercegovinu.backend.dto.CategoryDto;
import com.posjetihercegovinu.backend.entity.Category;
import com.posjetihercegovinu.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service : Spring prepoznaje ovu klasu kao "servisni sloj" i sam pravi jedan njen objekat
// koji drzi u pamcenju i daje svima kojima treba ( dependency injection)
@Service
// Lombok pravi konstruktor za sva final polja .Spring kroz taj konstruktor ubacuje repository
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAll(){
        return categoryRepository.findAll() // uzmi sve entitete iz baze
                .stream()   // pretvori listu u tok da se moze obraditi
                .map(this::toDto) // svaki entitet pretvori u DTO
                .toList(); // vrati nazad kao listu
    }

    // Pravi novu kategoriju iz podataka koju je poslao klijent
    public CategoryDto create(CategoryDto dto) {
        Category category = new Category(); // novi prazan entitet
        category.setName(dto.getName()); // prepisi naziv iz DTO
        category.setDescription(dto.getDescription());
        Category savedCategory = categoryRepository.save(category); // Sacuvaj u bazu (INSERT)
        return toDto(savedCategory); // vrati sacuvanu verziju (sada ima i ID)
    }

    // Pomocna metoda koja pretvara entitete u DTO
    private CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getDescription());
    }
}
