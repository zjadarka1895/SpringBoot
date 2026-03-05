package org.example.elementservice.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "simplified_genres") 
public class Genre {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "popularity_index")
    private Double popularityIndex;

    public Genre() {
    }

    public Genre(UUID id, String name, Double popularityIndex) {
        this.id = id;
        this.name = name;
        this.popularityIndex = popularityIndex;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public Double getPopularityIndex() { return popularityIndex; }

    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPopularityIndex(Double popularityIndex) { this.popularityIndex = popularityIndex; }

    // Uproszczone metody equals/hashCode oparte na ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("SimplifiedGenre{id=%s, name='%s'}", id, name);
    }
}