package org.example.categoryservice.dto.genre;

import java.util.UUID;

public class GenreDTO {

    private final UUID id;
    private final String name;
    private final Double popularityIndex;

    private GenreDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.popularityIndex = builder.popularityIndex;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPopularityIndex() {
        return popularityIndex;
    }

    @Override
    public String toString() {
        return String.format("%s (Popularity: %.1f)", name, popularityIndex);
    }

    // ==========================
    // ===== BUILDER
    // ==========================

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

        public GenreDTO build() {
            return new GenreDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
