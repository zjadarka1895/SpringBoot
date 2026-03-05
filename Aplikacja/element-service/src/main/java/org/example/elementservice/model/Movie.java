package org.example.elementservice.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import org.example.elementservice.model.Genre;

@Entity
@Table(name = "movies")
public class Movie implements Serializable, Comparable<Movie> {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column
    private Double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    //  Prywatny konstruktor 
    private Movie(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.releaseDate = builder.releaseDate;
        this.rating = builder.rating;
        this.genre = builder.genre;
    }

    //  Publiczny pusty konstruktor wymagany przez JPA
    public Movie() {}

    // ==========================
    // ===== GETTERY / SETTERY
    // ==========================

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    // ==========================
    // ===== BUILDER PATTERN
    // ==========================

    public static class Builder {
        private UUID id;
        private String title;
        private LocalDate releaseDate;
        private Double rating;
        private Genre genre;

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

        public Builder genre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // ==========================
    // ===== PORÓWNANIA / HASH / TEKST
    // ==========================

    @Override
    public int compareTo(Movie other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String genreName = (genre != null) ? genre.getName() : "Unknown";
        return String.format("%s (%d) - Rating: %.1f, Genre: %s",
                title,
                releaseDate != null ? releaseDate.getYear() : 0,
                rating != null ? rating : 0.0,
                genreName);
    }
}
