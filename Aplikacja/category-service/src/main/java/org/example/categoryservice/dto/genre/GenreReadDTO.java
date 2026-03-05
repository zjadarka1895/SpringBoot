package org.example.categoryservice.dto.genre;

import java.util.UUID;

public class GenreReadDTO {
    private UUID id;
    private String name;
    private Double popularityIndex;

    public GenreReadDTO() {}
    public GenreReadDTO(UUID id, String name, Double popularityIndex) {
        this.id = id; this.name = name; this.popularityIndex = popularityIndex;
    }
    public UUID getId() { return id; }
    public String getName() { return name; }
    public Double getPopularityIndex() { return popularityIndex; }
}
