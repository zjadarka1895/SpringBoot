// org.example.elementservice.repository.MovieRepository.java - Poprawiona wersja

package org.example.elementservice.repository;

import org.example.elementservice.model.Movie;
import org.example.elementservice.model.Genre; // Uproszczony model Genre
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    // Zwraca wszystkie filmy powiązane z danym ID Gatunku
    List<Movie> findAllByGenreId(UUID genreId);

    // Stare metody (wciąż użyteczne):
    List<Movie> findByGenre(Genre genre);
    List<Movie> findByGenre_Name(String genreName);
    Optional<Movie> findByTitleAndReleaseDate(String title, LocalDate releaseDate);
}