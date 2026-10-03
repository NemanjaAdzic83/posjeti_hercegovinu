package com.posjetihercegovinu.backend.entity;
// Anotacije iz JPA standarda kojima opisujemo tabelu
import jakarta.persistence.*;
// Lombok anotacije - generisu getter,setter, konstruktore umjesto nas
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Entity govori Hibernateu: "ova klasa predstavlja tabelu u bazi"
@Entity
// @Table odredjuje tacno ime tabele.Bez ovoga bi se koristilo ime klase
@Table(name = "categories")
// @Getter i @Setter : Lombok ih pravi sam , u pozadini
@Getter
@Setter
// @JPA zahtijeva prazan konstruktor(bez parametara) da bi mogao praviti objekte iz redova baze
@NoArgsConstructor
public class Category {
    // ovo polje je primarni kljuc, jedinstvrni identifikstor reda
    @Id
    // IDENTITY: baza sama povecava broj 1,2,3... pri svakom novom redu(AUTOINCREMENT u MySQL)
    @GeneratedValue
    private Long id;

    // nullable = false - kolona ne smije biti prazna,
    // unique = true - ne smiju postojati dvije kategorije istog imena
    // length = 100 - najvise 100 znakova(VARCHAR(100))
    @Column(nullable = false,unique = true, length = 100)
    private String name;

    // Opis je opcioni pa nema nullable i unique, a length = 500 daje dovoljno mjesta za kratak tekst
    @Column(length = 500)
    private String description;
}
