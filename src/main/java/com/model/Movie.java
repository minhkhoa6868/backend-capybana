package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "movies")
@Getter
@Setter

public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED NOT NULL")
    private Long id;
    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String title;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    @Column(columnDefinition = "DATE")
    private LocalDate releaseDate;
    @ManyToOne
    @JoinColumn(name = "categoryId", columnDefinition = "BIGINT UNSIGNED NOT NULL", referencedColumnName = "id")
    Category category;
}
