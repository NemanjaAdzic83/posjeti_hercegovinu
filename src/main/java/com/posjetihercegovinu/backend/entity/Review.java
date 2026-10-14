package com.posjetihercegovinu.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
// uniqueConstraints - baza sama garantuje da isti korisnik ne moze imati dvije recenzije
// za isto mjesto
@Table(name = "reviews", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","place_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ko je napisao recenziju
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    // Ocjena 1-5
    @Column(nullable = false)
    private int rating;

    // Komentar je opcion
    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // postavlja se automatski prije prvog spremanja
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }











}
