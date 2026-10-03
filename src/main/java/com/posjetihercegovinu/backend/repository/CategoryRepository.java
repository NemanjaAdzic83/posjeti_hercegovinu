package com.posjetihercegovinu.backend.repository;

import com.posjetihercegovinu.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Category,Long> - Category je entitet s kojim radimo, Long je tip njegovog id-a
// Samim nasledjivanjem dobijas gotove metode - save(), findAll(), findById(), deletedById()...
// Ne pise se nijedna linija SQL-a , Spring sam napravi implemantaciju
public interface CategoryRepository extends JpaRepository<Category,Long>{
}
