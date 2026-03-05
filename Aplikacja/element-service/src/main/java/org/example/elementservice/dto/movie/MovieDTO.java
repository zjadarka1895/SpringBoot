package org.example.elementservice.dto.movie;

import java.time.LocalDate;
import java.util.UUID;

public class MovieDTO {

    private final UUID id;
    private final String title;
    private final LocalDate releaseDate;
    private final Double rating;
    private final String genreName;  // zamiast całego obiektu Genre

    private MovieDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.releaseDate = builder.releaseDate;
        this.rating = builder.rating;
        this.genreName = builder.genreName;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public String getGenreName() {
        return genreName;
    }

    @Override
    public String toString() {
        return String.format("%s (%d), Rating: %.1f, Genre: %s",
                title,
                releaseDate != null ? releaseDate.getYear() : 0,
                rating != null ? rating : 0.0,
                genreName);
    }

    // ==========================
    // ===== BUILDER
    // ==========================

    public static class Builder {
        private UUID id;
        private String title;
        private LocalDate releaseDate;
        private Double rating;
        private String genreName;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public Builder genreName(String genreName) {
            this.genreName = genreName;
            return this;
        }

        public MovieDTO build() {
            return new MovieDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
