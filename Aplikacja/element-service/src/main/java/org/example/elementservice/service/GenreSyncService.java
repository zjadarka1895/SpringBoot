// org.example.elementservice.service.GenreSyncService.java

package org.example.elementservice.service;

import org.example.elementservice.dto.genre.GenreEventDTO;
import org.example.elementservice.model.Genre;
import org.example.elementservice.model.Movie; 
import org.example.elementservice.repository.GenreRepository;
import org.example.elementservice.repository.MovieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GenreSyncService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    public GenreSyncService(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }

    // ===========================================
    // ===== ZARZĄDZANIE DANYMI LOKALNYMI (dla MovieController)
    // ===========================================


    @Transactional(readOnly = true)
    public Optional<Genre> findById(UUID id) {
        return genreRepository.findById(id);
    }


    // ===========================================
    // ===== OBSŁUGA EVENTÓW (dla GenreEventController)
    // ===========================================

    /**
     * Obsługuje eventy CREATE/UPDATE dla Gatunku (Upsert).
     */
    @Transactional
    public void upsertGenre(GenreEventDTO dto) {
        Genre existingGenre = genreRepository.findById(dto.getId())
                .orElse(new Genre());

        // Ustawienie ID jest kluczowe, aby JPA wiedziało, czy aktualizować czy tworzyć.
        existingGenre.setId(dto.getId());
        existingGenre.setName(dto.getName());
        existingGenre.setPopularityIndex(dto.getPopularityIndex());

        genreRepository.save(existingGenre);
        System.out.printf("Synchronizacja: Zaktualizowano/Utworzono Gatunek ID: %s (%s)%n", dto.getId(), dto.getName());
    }

    /**
     * Obsługuje event DELETE dla Gatunku. Usuwa powiązane filmy, a następnie uproszczony rekord Gatunku.
     */
    @Transactional
    public void deleteGenreAndAssociatedMovies(UUID genreId) {
        // 1. Znajduje i usuwa powiązane filmy
        List<Movie> moviesToDelete = movieRepository.findAllByGenreId(genreId);

        if (!moviesToDelete.isEmpty()) {
            movieRepository.deleteAll(moviesToDelete);
            System.out.printf("Synchronizacja: Usunięto %d filmów powiązanych z ID: %s%n", moviesToDelete.size(), genreId);
        }

        // 2. Usuwa uproszczony rekord gatunku
        if (genreRepository.existsById(genreId)) {
            genreRepository.deleteById(genreId);
            System.out.printf("Synchronizacja: Usunięto rekord Gatunku ID: %s%n", genreId);
        } else {
            System.out.printf("Synchronizacja: Rekord Gatunku ID: %s nie znaleziono - pomijam usuwanie%n", genreId);
        }
    }
}