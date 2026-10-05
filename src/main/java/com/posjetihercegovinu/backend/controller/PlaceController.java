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

    // GET /api/places/5 -> detalji jednog mjesta
    // @PathVariable: vrijednost iz URL-a ({id}) ubacuje se u parametar id
    @GetMapping("/{id}")
    public PlaceDto getById(@PathVariable Long id){
        return placeService.getById(id);
    }

    // PUT /api/places/5 -> izmjena mjesta, isto kao POST, NE ZABORAVI @Valid
    @PutMapping("/{id}")
    public PlaceDto update(@PathVariable Long id, @Valid @RequestBody PlaceDto dto){
        return placeService.update(id, dto);
    }

    // DELETE /api/places/5 -> brisanje. Odgovor 204 Not Content znaci uspjelo , nema se sta vratiti
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        placeService.delete(id);
    }


}
