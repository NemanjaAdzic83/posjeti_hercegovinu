package com.posjetihercegovinu.backend.repository;

import com.posjetihercegovinu.backend.entity.Accommodation;
import com.posjetihercegovinu.backend.entity.AccommodationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {

    // Pretraga po nazivu i opisu, uz opcioni filter po tipu
    // Pretraga po nazivu i opisu, uz opcioni filter po tipu.
    // (:type IS NULL OR ...) znači: ako tip nije poslat, filter se preskače.
    @Query("""
            SELECT a FROM Accommodation a
            WHERE (LOWER(a.name) LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(a.description) LIKE LOWER(CONCAT('%', :q, '%')))
              AND (:type IS NULL OR a.type = :type)
            """)
    Page<Accommodation> search(@Param("q") String q,
                               @Param("type") AccommodationType type,
                               Pageable pageable);
}
