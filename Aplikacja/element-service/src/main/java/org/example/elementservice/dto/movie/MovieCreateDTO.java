// org.example.elementservice.dto.movie.MovieCreateDTO.java

package org.example.elementservice.dto.movie;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID; // Import dla UUID

public class MovieCreateDTO {
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private Double rating;
    private UUID genreId; //

    public MovieCreateDTO() {}
    public MovieCreateDTO(String title, LocalDate releaseDate, Double rating, UUID genreId) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.genreId = genreId;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public UUID getGenreId() { return genreId; } // ⬅️ Dodany getter
    public void setGenreId(UUID genreId) { this.genreId = genreId; } // ⬅️ Dodany setter
}