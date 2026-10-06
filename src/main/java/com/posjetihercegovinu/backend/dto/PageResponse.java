package com.posjetihercegovinu.backend.dto;

import java.util.List;

// Genericki record za bilo koju stranicu rezultata. <T> je zamjena za svaki tip
// PageResponse<PlaceDto> je stranica mjesta, kasnije PageResponse<RestaurantDto> itd.
public record PageResponse<T>(
        List<T> content, // stavke na ovoj sranici
        int page,        // broj trenutne stranice
        int size,       // trazena velicina stranice
        long totalItems,    // ukupan broj stavki u bazi(koje odgovaraju filteru)
        int totalPages,     // ukupan broj stranica
        boolean last        // true ako je ovo zadnja stranica
) {
}
