// org.example.elementservice.dto.movie.MovieUpdateDTO.java

package org.example.elementservice.dto.movie;

import java.time.LocalDate;
import java.util.UUID;

public class MovieUpdateDTO {

    private String title;
    private LocalDate releaseDate;
    private Double rating;
    private UUID genreId;

    // Publiczny pusty konstruktor
    public MovieUpdateDTO() {}

    // Gettery i Settery

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public UUID getGenreId() {
        return genreId;
    }

    public void setGenreId(UUID genreId) {
        this.genreId = genreId;
    }
}