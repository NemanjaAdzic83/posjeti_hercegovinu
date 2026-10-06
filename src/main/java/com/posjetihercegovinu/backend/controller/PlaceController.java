package com.posjetihercegovinu.backend.controller;

import com.posjetihercegovinu.backend.dto.PageResponse;
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

//    @GetMapping                              OBRISATI OVU METODU, dva @GetMappinga na istoj putanji ruse aplikaciju
//    public List<PlaceDto> getAll(){
//        return placeService.getAll();
//    }


    // GET /api/places?q=most&categoryId=2&page=0&size=10&sortBy=name&direction=asc
    //@RequestParam - vrijednost iz URL-a poslije znaka "?" ide u parametr metode
    // required = false - parametar je opcion
    // defaultValue - sta se koristi ako ga klijent ne posalje
    @GetMapping
    public PageResponse<PlaceDto> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        return placeService.search(q,categoryId,page,size,sortBy,direction);
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
