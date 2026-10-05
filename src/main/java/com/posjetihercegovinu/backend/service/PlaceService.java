package com.posjetihercegovinu.backend.service;

import com.posjetihercegovinu.backend.dto.PlaceDto;
import com.posjetihercegovinu.backend.entity.Category;
import com.posjetihercegovinu.backend.entity.Place;
import com.posjetihercegovinu.backend.repository.CategoryRepository;
import com.posjetihercegovinu.backend.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.posjetihercegovinu.backend.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    // Treba nam PlaceRepository za mjesta, a CategoryRepository da pronadjemo kategoriju po ID-ju
    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;

    // @Transactional(readOnly = true) : sve se radi unutar jedne sesije sa bazom
    // To nam treba jer je kategorija Place ucitana LAZY tj tek kad zatreba
    // a to radi samo dok je sesija otvorena. Bez ovoga bi pri citanju naziva kategorije dobili gresku
    @Transactional(readOnly = true)
    public List<PlaceDto> getAll() {
        return placeRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // @Transactiona bez "readOnly" , ovde pisemo u bazu
    @Transactional
    public PlaceDto create(PlaceDto dto) {
        // Pronadji kategoriju po ID-ju. Ako ne postoji ,bacamo izuzetak.
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Kategorija sa ID " + dto.getCategoryId() + " ne postoji"));

        Place place = new Place();
        place.setName(dto.getName());
        place.setDescription(dto.getDescription());
        place.setCategory(category); // povezujemo mjesto sa kategorijom
        place.setAddress(dto.getAddress());
        place.setLatitude(dto.getLatitude());
        place.setLongitude(dto.getLongitude());
        place.setOpeningHours(dto.getOpeningHours());
        place.setPrice(dto.getPrice());
        place.setImageUrl(dto.getImageUrl());

        Place saved = placeRepository.save(place);
        return toDto(saved);

    }

    @Transactional(readOnly = true)
    public PlaceDto getById(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mjesto sa ID " + id + " ne postoji"));
        return toDto(place);
    }

    // Mjenja postojece mjesto : ucitamo ga iz baze i prepisemo polja novim vrijednostima
    @Transactional
    public PlaceDto update(Long id, PlaceDto dto) {
        // Pronadji mjesto koje se mjenja
        Place place = placeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mjesto sa ID " + id + " ne postoji"));

        // Pronadji (moguce novu ) kategoriju
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategorija sa ID " + dto.getCategoryId() + " ne postoji"));

        // Prepisati sva polja , iD se nem mjenja
        place.setName(dto.getName());
        place.setDescription(dto.getDescription());
        place.setCategory(category);
        place.setAddress(dto.getAddress());
        place.setLatitude(dto.getLatitude());
        place.setLongitude(dto.getLongitude());
        place.setOpeningHours(dto.getOpeningHours());
        place.setPrice(dto.getPrice());
        place.setImageUrl(dto.getImageUrl());

        // Sacuvati , posto entitet vec ima ID, Hibernate radi UPDATE, a ne INSERT
        return  toDto(placeRepository.save(place));
    }

    // Brise mjesto po ID-ju
    @Transactional
    public void delete(Long id) {
        // Prvo provjeriti da li postoji
        if (!placeRepository.existsById(id)) {
            throw  new ResourceNotFoundException("Mjesto sa ID " + id + " ne postoji");
        }
        placeRepository.deleteById(id);
    }




    // Pretvara entitet u DTO, ovde citamo i naziv kategorije
    private PlaceDto toDto(Place place) {
        PlaceDto dto = new PlaceDto();
        dto.setId(place.getId());
        dto.setName(place.getName());
        dto.setDescription(place.getDescription());

        // mjesto bi teoretski moglo biti bez kategorije, pa provjeravamo da nije null
        if (place.getCategory() != null) {
            dto.setCategoryId(place.getCategory().getId());
            dto.setCategoryName(place.getCategory().getName());
        }

        dto.setAddress(place.getAddress());
        dto.setLatitude(place.getLatitude());
        dto.setLongitude(place.getLongitude());
        dto.setOpeningHours(place.getOpeningHours());
        dto.setPrice(place.getPrice());
        dto.setImageUrl(place.getImageUrl());

        return dto;
    }

}
