package org.example.categoryservice.dto.genre;

public class GenreCreateDTO {
    private String name;
    private Double popularityIndex;

    public GenreCreateDTO() {}
    public GenreCreateDTO(String name, Double popularityIndex) {
        this.name = name;
        this.popularityIndex = popularityIndex;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPopularityIndex() { return popularityIndex; }
    public void setPopularityIndex(Double popularityIndex) { this.popularityIndex = popularityIndex; }
}
