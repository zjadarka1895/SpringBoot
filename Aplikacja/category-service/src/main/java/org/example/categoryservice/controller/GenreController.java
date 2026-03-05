package org.example.categoryservice.controller;

import org.example.categoryservice.dto.genre.GenreCollectionDTO; // Załóżmy, że DTO jest w podpakietach
import org.example.categoryservice.dto.genre.GenreCreateDTO;
import org.example.categoryservice.dto.genre.GenreReadDTO;

import org.example.categoryservice.model.Genre;
import org.example.categoryservice.service.GenreService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // GET /api/genres  -> kolekcja (id + name)
    @GetMapping
    public List<GenreCollectionDTO> listGenres() {
        return genreService.findAll().stream()
                // Upewnij się, że GenreCollectionDTO w Category Service przyjmuje UUID i String
                .map(g -> new GenreCollectionDTO(g.getId(), g.getName()))
                .collect(Collectors.toList());
    }

    // POST /api/genres  -> create
    @PostMapping
    public ResponseEntity<GenreReadDTO> createGenre(@RequestBody GenreCreateDTO dto) {
        // Upewnij się, że Genre.builder() jest dostępny
        Genre genre = Genre.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .popularityIndex(dto.getPopularityIndex())
                .build();
        Genre saved = genreService.save(genre);
        GenreReadDTO read = new GenreReadDTO(saved.getId(), saved.getName(), saved.getPopularityIndex());
        return ResponseEntity.created(URI.create("/api/genres/" + saved.getId())).body(read);
    }

    // GET /api/genres/{id} -> read
    @GetMapping("/{id}")
    public ResponseEntity<GenreReadDTO> getGenre(@PathVariable UUID id) {
        return genreService.findById(id)
                .map(g -> ResponseEntity.ok(new GenreReadDTO(g.getId(), g.getName(), g.getPopularityIndex())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /api/genres/{id} -> update
    @PutMapping("/{id}")
    public ResponseEntity<GenreReadDTO> updateGenre(@PathVariable UUID id, @RequestBody GenreCreateDTO dto) {
        return genreService.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setPopularityIndex(dto.getPopularityIndex());
            Genre saved = genreService.save(existing);
            GenreReadDTO read = new GenreReadDTO(saved.getId(), saved.getName(), saved.getPopularityIndex());
            return ResponseEntity.ok(read);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/genres/{id} -> delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable UUID id) {
        if (!genreService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}