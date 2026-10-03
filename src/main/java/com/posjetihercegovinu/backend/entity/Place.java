package com.posjetihercegovinu.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "places")
@Getter
@Setter
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    // columnDefinition = "TEXT" - dugi tekst
    // Obican String je ogranicen na 255 znakova , a opis mjesta je obicno duzi
    @Column(columnDefinition = "TEXT")
    private String description;

    // Veza vie mjesta jedna kategorija
    // LAZY - kategorija koja se ucitava iz baze kad ti zatreba(brze)
    // @JoinColumn - u tabeli places se pravi kolona category_id koja pokazuje na categories.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 300)
    private String address;

    // GPS koordinate, BigDecimal se koristi jer je tacan(double moze praviti sitne greske u zaokruzivanju)
    // precision = 10, scale = 7 - ukupno 10 cifara, od toga 7 iza decimalne tacke
    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "opening_hours", length = 200)
    private String openingHours;

    // Cijena ulaznice, ako postoji
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    // Privremeno jedna slika po mjestu, kasnije prelazimo na tabelu images
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // @PrePersist - Hibernate poziva ovu metodu automatski PRIJE prvog spremanja u bazu
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // @PreUpdate - poziva se automatski PRIJE svake izmjene postojeceg reda
    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
