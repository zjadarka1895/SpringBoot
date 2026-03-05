package org.example.elementservice.dto.movie;

import java.time.LocalDate;
import java.util.UUID;

public class MovieCollectionDTO {
    private UUID id;
    private String title;
    private LocalDate releaseDate;

    public MovieCollectionDTO() {}
    public MovieCollectionDTO(UUID id, String title, LocalDate releaseDate) {
        this.id = id; this.title = title; this.releaseDate = releaseDate;
    }
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public LocalDate getReleaseDate() { return releaseDate; }
}
