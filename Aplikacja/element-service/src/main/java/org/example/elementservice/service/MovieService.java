// org.example.elementservice.service.MovieService.java

package org.example.elementservice.service;

import org.example.elementservice.dto.movie.MovieReadDTO; // ⬅️ Zmieniamy na MovieReadDTO
import org.example.elementservice.model.Movie;
import org.example.elementservice.model.Genre;
import org.example.elementservice.repository.MovieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // ===================================
    // ===== CRUD
    // ===================================

    public boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate) {
        return movieRepository.findByTitleAndReleaseDate(title, releaseDate).isPresent();
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(UUID id) {
        return movieRepository.findById(id);
    }

    public List<Movie> findByGenre(Genre genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> findByGenreName(String genreName) {
        return movieRepository.findByGenre_Name(genreName);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteById(UUID id) {
        movieRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return movieRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public Optional<MovieReadDTO> findDTOById(UUID id) {
        return movieRepository.findById(id)
                .map(this::mapToReadDTO);
    }


    public MovieReadDTO mapToReadDTO(Movie movie) {
        return new MovieReadDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getRating(),
                // 1. Dodano przekazanie genreId
                movie.getGenre() != null ? movie.getGenre().getId() : null,
                // 2. Przekazanie genreName
                movie.getGenre() != null ? movie.getGenre().getName() : "N/A"
        );
    }

    @Transactional(readOnly = true)
    public List<MovieReadDTO> findAllDTO() { 
        return movieRepository.findAll().stream()
                .map(this::mapToReadDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovieReadDTO> findByGenreDTO(Genre genre) { 
        return movieRepository.findByGenre(genre).stream()
                .map(this::mapToReadDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovieReadDTO> findByGenreNameDTO(String genreName) { 
        return movieRepository.findByGenre_Name(genreName).stream()
                .map(this::mapToReadDTO)
                .toList();
    }


}