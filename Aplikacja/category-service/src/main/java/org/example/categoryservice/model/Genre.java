package org.example.categoryservice.model; // 🟢 POPRAWNY PAKIET DLA SERWISU KATEGORII

import jakarta.persistence.*;
import java.util.*;


@Entity
@Table(name = "genres")
public class Genre implements Comparable<Genre> {

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

    private Genre(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.popularityIndex = builder.popularityIndex;
    }

    // ===================================
    // ===== BUILDER (Zachowany)
    // ===================================

    public static class Builder {
        private UUID id;
        private String name;
        private Double popularityIndex;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder popularityIndex(Double popularityIndex) {
            this.popularityIndex = popularityIndex;
            return this;
        }

        public Genre build() {
            // Upewniamy się, że ID jest ustawione, jeśli nie zostało podane jawnie (choć z reguły builder wymusza)
            if (this.id == null) {
                this.id = UUID.randomUUID();
            }
            return new Genre(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // ===================================
    // ===== GETTERY I SETTERY
    // ===================================

    public UUID getId() { return id; }
    public String getName() { return name; }
    public Double getPopularityIndex() { return popularityIndex; }

    public void setName(String name) { this.name = name; }
    // 🟢 POPRAWIONY SETTER: Ustawia Double (oryginalnie przyjmował int, co było niezgodne z polem)
    public void setPopularityIndex(Double popularityIndex) { this.popularityIndex = popularityIndex; }



    // ===================================
    // ===== METODY WSPOMAGAJĄCE
    // ===================================

    @Override
    public int compareTo(Genre other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return String.format("Genre{id=%s, name='%s', popularityIndex=%.1f}", id, name, popularityIndex);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Genre)) return false;
        Genre other = (Genre) obj;
        return Objects.equals(id, other.id);
    }
}