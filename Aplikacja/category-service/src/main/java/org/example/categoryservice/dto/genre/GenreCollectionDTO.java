package org.example.categoryservice.dto.genre;

import java.util.UUID;

public class GenreCollectionDTO {
    private UUID id;
    private String name;

    public GenreCollectionDTO() {}
    public GenreCollectionDTO(UUID id, String name) { this.id = id; this.name = name; }
    public UUID getId() { return id; }
    public String getName() { return name; }
}
