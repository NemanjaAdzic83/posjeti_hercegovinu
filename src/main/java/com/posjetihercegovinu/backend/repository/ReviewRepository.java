package com.posjetihercegovinu.backend.repository;

import com.posjetihercegovinu.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Sve recenzije jednog mjesta, najnovije prvo
   // List <Review> findByPlaceInOrderByCreatedAtDesc(Long placeId);

    // Recenzija odredjenog korisnika za odredjeno mjesto
   // Optional<Review> findByUserIdAndPlaceId(Long userId, Long placeId);

    // Prosjecna ocjena mjesta, AVG vraca null ako nema nijedne recenzije
  //  @Query("SELECT AVG(r.rating) FROM Review r WHERE r.place.id = :placeId")
  //  Double findAverageRating(@Param("place_id") Long placeId);

    // broj recenzija mjesta
  //  long countByPlaceId(Long placeId);
}
