package com.posjetihercegovinu.backend.repository;

import com.posjetihercegovinu.backend.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place,Long> {
}
