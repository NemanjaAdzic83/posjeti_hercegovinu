package com.posjetihercegovinu.backend.controller;

import com.posjetihercegovinu.backend.dto.PlaceDto;
import com.posjetihercegovinu.backend.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public List<PlaceDto> getAll(){
        return placeService.getAll();
    }

    // POST api/places : dodavanje novog mjesta
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceDto create(@Valid @RequestBody PlaceDto dto){
        return placeService.create(dto);
    }
}
