// org.example.elementservice.controller.MovieController.java

package org.example.elementservice.controller;

import org.example.elementservice.dto.movie.MovieCreateDTO;
import org.example.elementservice.dto.movie.MovieReadDTO;
import org.example.elementservice.dto.movie.MovieUpdateDTO;
import org.example.elementservice.model.Movie;
import org.example.elementservice.model.Genre;
import org.example.elementservice.service.MovieService;
import org.example.elementservice.service.GenreSyncService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final GenreSyncService genreSyncService; // serwis synchronizacyjnego do walidacji

    // Konstruktor z wstrzykiwaniem zależności
    public MovieController(MovieService movieService, GenreSyncService genreSyncService) {
        this.movieService = movieService;
        this.genreSyncService = genreSyncService;
    }

    // ===================================
    // ===== CRUD
    // ===================================

    /** Zwraca listę wszystkich filmów. */
    @GetMapping
    public List<MovieReadDTO> getAllMovies(@RequestParam(required = false) UUID genreId) {
        if (genreId != null) {
            return genreSyncService.findById(genreId)
                    .map(movieService::findByGenreDTO)
                    .orElse(Collections.emptyList());
        }
        return movieService.findAllDTO();
    }

    /** Zwraca film po ID. */
    @GetMapping("/{id}")
    public ResponseEntity<MovieReadDTO> getMovieById(@PathVariable UUID id) {
        // Zakładamy, że findDTOById jest zaimplementowane w MovieService
        return movieService.findDTOById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono filmu o ID: " + id));
    }

    /** Tworzy nowy film. */
    @PostMapping()
    public ResponseEntity<MovieReadDTO> createMovie(@RequestBody MovieCreateDTO createDTO) {

        Optional<Genre> optionalGenre = genreSyncService.findById(createDTO.getGenreId());

        return optionalGenre
                .map(genre -> {
                    // 2. Budowanie encji Movie
                    Movie movie = Movie.builder()
                            .id(UUID.randomUUID())
                            .title(createDTO.getTitle())
                            .releaseDate(createDTO.getReleaseDate())
                            .rating(createDTO.getRating())
                            .genre(genre) // Przypisanie obiektu Genre
                            .build();

                    // 3. Zapis i konwersja na DTO
                    Movie savedMovie = movieService.save(movie);
                    MovieReadDTO readDTO = movieService.mapToReadDTO(savedMovie);

                    return ResponseEntity.created(URI.create("/api/movies/" + savedMovie.getId())).body(readDTO);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Nie znaleziono gatunku o ID: " + createDTO.getGenreId() + " w lokalnej bazie. Proszę użyć istniejącego ID gatunku."
                ));
    }

    /** Aktualizuje istniejący film. */
    @PutMapping("/{id}")
    public MovieReadDTO updateMovie(@PathVariable UUID id, @RequestBody MovieUpdateDTO updateDTO) {
        return movieService.findById(id)
                .map(movie -> {
                    // Aktualizacja pól
                    if (updateDTO.getTitle() != null) movie.setTitle(updateDTO.getTitle());
                    if (updateDTO.getReleaseDate() != null) movie.setReleaseDate(updateDTO.getReleaseDate());
                    if (updateDTO.getRating() != null) movie.setRating(updateDTO.getRating());

                    // Aktualizacja gatunku, jeśli podano nowy ID
                    if (updateDTO.getGenreId() != null) {
                        genreSyncService.findById(updateDTO.getGenreId())
                                .ifPresentOrElse(
                                        movie::setGenre,
                                        () -> { throw new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST,
                                                "Nie znaleziono gatunku o ID: " + updateDTO.getGenreId() + " dla aktualizacji."
                                        );}
                                );
                    }

                    Movie updatedMovie = movieService.save(movie);
                    // Zakładamy, że mapToReadDTO jest zaimplementowane w MovieService
                    return movieService.mapToReadDTO(updatedMovie);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono filmu o ID: " + id));
    }

    /** Usuwa film po ID. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable UUID id) {
        if (!movieService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono filmu o ID: " + id);
        }
        movieService.deleteById(id);
    }


}
