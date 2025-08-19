package com.example.urlshortener.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private String originalUrl;
    @Column(unique = true)
    private String shortCode;
    @Column(nullable = false)
    private LocalDateTime creationDate;
    private long clickCount;
    private LocalDateTime expirationDate;


}
