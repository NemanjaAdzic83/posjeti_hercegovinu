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
import com.posjetihercegovinu.backend.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;
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

    // Dozvoljena polja za sortiranje. Ako bismo dozvolili bilo sta , klijent bi mogao poslati
    // sortBy=nesto i dobio bi gresku 500
    private static final Set<String> SORT_FIELDS = Set.of("name", "price", "createdAt");

    @Transactional(readOnly = true)
    public PageResponse<PlaceDto> search(String q, Long categoryId,
                                         int page, int size,String sortBy, String direction){

        // Ako q nije poslat, koristimo prazan tekst(pogadja sve).trim() skida razmake
        String query = (q == null) ? "" : q.trim();

        // Zastita od losih vrjednosti - stranica ne moze biti negativna
        // a velicina je izmedji 1 i 50, da neko ne bi trazio veliki broj redova odjednom
        int safePage = Math.max(page,0);
        int safeSize = Math.min(Math.max(size,1),50);

        // Ako sortBy nije na listi dozvoljenih, sortiramo po nazivu
        String safeSortBy = SORT_FIELDS.contains(sortBy) ? sortBy : "name";

        // "desc" - opadajuce (Z-A), sve ostalo rastuce (A-Z)
        Sort sort = "desc".equalsIgnoreCase(direction) ? Sort.by(safeSortBy).descending() : Sort.by(safeSortBy).ascending();

        // Pageable - koja stranica, koliko stavki , kojim redopsljedom
        Pageable pageable = PageRequest.of(safePage,safeSize,sort);

        // Pozivamo upit , baza vraca samo traenu sranicu
        Page<Place> result = placeRepository.search(query,categoryId,pageable);

        // Svako mjesto pretvaramo u DTO i pakujemo u PageResponse
        return new PageResponse<>(
                result.getContent().stream().map(this::toDto).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );


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
