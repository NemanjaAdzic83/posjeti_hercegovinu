package com.posjetihercegovinu.backend.repository;

import com.posjetihercegovinu.backend.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place,Long> {

    // @Query : sami pisemo upit> Ovo je JPQL , slicno SQL-u ali radi sa Java klasama i poljima
    // (Place, p.name), a ne sa tabelama i kolonama
    //
     // Citanje upita:
    // SELECT p FROM Place p  - uzmi mjesta
    // LOWER(...)             - sva slova mala , da Most i most budu isto
    // LIKE '%tekst%          - sadrzi taj tekst bilo gdje
    // (:categoryId IS NULL OR..) - ako filter kategorije nije poslat preskoci ga
    // Pageable                 - Spring sam dodaje LIMIT/OFFSET i ORDER BY


    @Query("""
            SELECT p FROM Place p WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%')))
            AND (:categoryId IS NULL OR p.category.id = :categoryId)
""")
    Page<Place> search(@Param("q") String q,
                       @Param("categoryId") Long categoryId,
                       Pageable pageable);
}
