// org.example.elementservice.controller.GenreEventController.java

package org.example.elementservice.controller;

import org.example.elementservice.dto.genre.GenreEventDTO;
import org.example.elementservice.service.GenreSyncService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/events/genres")
public class GenreEventController {

    private final GenreSyncService genreSyncService;

    public GenreEventController(GenreSyncService genreSyncService) {
        this.genreSyncService = genreSyncService;
    }

    // POST: Obsługuje CREATE i UPDATE
    @PostMapping
    public ResponseEntity<Void> handleUpsertEvent(@RequestBody GenreEventDTO dto) {
        genreSyncService.upsertGenre(dto);
        return ResponseEntity.ok().build();
    }

    // DELETE: Obsługuje DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> handleDeleteEvent(@PathVariable UUID id) {
        genreSyncService.deleteGenreAndAssociatedMovies(id);
        return ResponseEntity.noContent().build();
    }

}