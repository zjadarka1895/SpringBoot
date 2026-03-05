package org.example.elementservice.dto.movie;

import java.time.LocalDate;
import java.util.UUID;

public class MovieReadDTO {
    private UUID id;
    private String title;
    private LocalDate releaseDate;
    private Double rating;
    private UUID genreId;
    private String genreName;

    public MovieReadDTO() {}
    public MovieReadDTO(UUID id, String title, LocalDate releaseDate, Double rating, UUID genreId, String genreName) {
        this.id = id; this.title = title; this.releaseDate = releaseDate; this.rating = rating;
        this.genreId = genreId; this.genreName = genreName;
    }
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public Double getRating() { return rating; }
    public UUID getGenreId() { return genreId; }
    public String getGenreName() { return genreName; }
}
