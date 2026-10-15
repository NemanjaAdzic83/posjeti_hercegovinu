package com.posjetihercegovinu.backend.controller;

import com.posjetihercegovinu.backend.dto.AccommodationDto;
import com.posjetihercegovinu.backend.dto.PageResponse;
import com.posjetihercegovinu.backend.entity.AccommodationType;
import com.posjetihercegovinu.backend.service.AccommodationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService service;

    @GetMapping
    public PageResponse<AccommodationDto> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false)AccommodationType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return service.search(q, type, page, size, sortBy, direction);

    }

    // detalji jednog smjestaja
    @GetMapping("/{id}")
    public AccommodationDto getById(@PathVariable Long id){
        return service.getById(id);
    }

    // dodavanje, ( samo ADMIN )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccommodationDto create(@Valid @RequestBody AccommodationDto dto){
        return service.create(dto);
    }

    // izmjena ( samo ADMIN)
    @PutMapping("/{id}")
    public AccommodationDto update(@PathVariable Long id, @Valid @RequestBody AccommodationDto dto){
        return service.update(id, dto);
    }

    // brisanje, ( samo ADMIN )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }











}
