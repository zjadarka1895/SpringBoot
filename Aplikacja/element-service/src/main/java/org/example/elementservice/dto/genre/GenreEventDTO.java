// org.example.elementservice.dto.genre.GenreEventDTO.java

package org.example.elementservice.dto.genre;

import java.util.UUID;

// Używamy nazwy GenreEventDTO, aby podkreślić, że jest to DTO używane do komunikacji między serwisami (eventy)

public class GenreEventDTO {

    private UUID id;
    private String name;
    private Double popularityIndex;

    // Pusty konstruktor wymagany do deserializacji JSON przez Spring/Jackson
    public GenreEventDTO() {}

    // Konstruktor do ewentualnego tworzenia obiektu
    public GenreEventDTO(UUID id, String name, Double popularityIndex) {
        this.id = id;
        this.name = name;
        this.popularityIndex = popularityIndex;
    }

    // Gettery i Settery (wymagane do deserializacji)

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularityIndex() {
        return popularityIndex;
    }

    public void setPopularityIndex(Double popularityIndex) {
        this.popularityIndex = popularityIndex;
    }
}