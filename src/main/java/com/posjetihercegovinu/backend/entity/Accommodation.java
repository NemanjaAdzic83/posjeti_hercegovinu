package com.posjetihercegovinu.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accommodations")
@Getter
@Setter
@NoArgsConstructor
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccommodationType type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 300)
    private String address;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "price_from", precision = 10, scale = 2)
    private BigDecimal priceFrom;

    @Column(length = 50)
    private String phone;

    @Column(length = 300)
    private String website;

    @Column(name = "image_url",length = 500)
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
