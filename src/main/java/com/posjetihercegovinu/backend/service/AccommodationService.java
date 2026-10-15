package com.posjetihercegovinu.backend.service;

import com.posjetihercegovinu.backend.dto.AccommodationDto;
import com.posjetihercegovinu.backend.dto.PageResponse;
import com.posjetihercegovinu.backend.entity.Accommodation;
import com.posjetihercegovinu.backend.entity.AccommodationType;
import com.posjetihercegovinu.backend.exception.ResourceNotFoundException;
import com.posjetihercegovinu.backend.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository repository;

    // Dozvoljena polja za sortiranje (zastita od nepostojecih naziva polja)
    private static final Set<String> SORT_FIELDS = Set.of("name","priceFrom","createdAt");

    // Pretraga sa filterom po tim=pu, pagnacijom i sortiranjem
    public PageResponse<AccommodationDto> search(String q, AccommodationType type,int page,
                                                 int size, String sortBy, String direction){
        String query = (q == null) ? "" : q.trim();
        int safePage = Math.max(page,0);
        int safeSize = Math.min(Math.max(size,1),50);
        String safeSortBy = SORT_FIELDS.contains(sortBy) ? sortBy : "name";
        Sort sort = "desc".equalsIgnoreCase(direction) ? Sort.by(safeSortBy).descending()
                : Sort.by(safeSortBy).ascending();
        Pageable pageable = PageRequest.of(safePage,safeSize,sort);

        Page<Accommodation> result = repository.search(query,type,pageable);
        return new PageResponse<>(
                result.getContent().stream().map(this::toDto).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    // Vraca jedan smjestaj , ako ne postoji baca exception
    @Transactional(readOnly = true)
    public AccommodationDto getById(Long id){
        return toDto(find(id));
    }

    // Pravi novi smjestaj iz podataka klijenta
    public AccommodationDto create(AccommodationDto dto){
        Accommodation entity = new Accommodation();
        apply(entity,dto);
        return toDto(repository.save(entity));
    }

    // Mijenja postojeci smjestaj (prepisuje sva polja osim ID-ja)
    public AccommodationDto update(Long id, AccommodationDto dto){
        Accommodation entity = find(id);
        apply(entity,dto);
        return toDto(repository.save(entity));
    }

    // Brise smjestaj, ako ne postoji -404
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Smjestaj sa id " + id + " ne postoji(Accommodation not found)");
        }
        repository.deleteById(id);
    }


    // pomocna metoda - trazi smjestaj po ID-ju
    private Accommodation find(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Smjestaj sa id " + id + " ne postoji"));

    }

    // Pomocna metoda - prepisuje polja iz DTO-a u entitet ( koristi je create i update,
    // da isti kod ne pisemo dva puta i da se polje ne zaboravi na jednom mjestu)
    private void apply(Accommodation e, AccommodationDto dto){
        e.setName(dto.getName());
        e.setType(dto.getType());
        e.setDescription(dto.getDescription());
        e.setAddress(dto.getAddress());
        e.setLatitude(dto.getLatitude());
        e.setLongitude(dto.getLongitude());
        e.setPriceFrom(dto.getPriceFrom());
        e.setPhone(dto.getPhone());
        e.setWebsite(dto.getWebsite());
        e.setImageUrl(dto.getImageUrl());

    }



    // Pretvara entitet u DTO
    private AccommodationDto toDto(Accommodation e) {
        return new AccommodationDto(e.getId(),e.getName(),e.getType(),e.getDescription(),
                e.getAddress(),e.getLatitude(), e.getLongitude(), e.getPriceFrom(), e.getPhone(),
                e.getWebsite(), e.getImageUrl());
    }

}
