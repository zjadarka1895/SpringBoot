package org.example.categoryservice.initializer;

import org.example.categoryservice.model.Genre;
import org.example.categoryservice.service.GenreService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final GenreService genreService;

    public DataInitializer(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n=== Ładowanie danych startowych do bazy (Category Service) ===");

        if (genreService.findAll().isEmpty()) {

            // 1. Inicjalizacja Science Fiction
            Genre sciFi = Genre.builder()
                    .id(UUID.randomUUID())
                    .name("Science Fiction")
                    .popularityIndex(8.7)
                    .build();

            // 2. Inicjalizacja Dramy
            Genre drama = Genre.builder()
                    .id(UUID.randomUUID())
                    .name("Drama")
                    .popularityIndex(7.8)
                    .build();

            genreService.save(sciFi);
            genreService.save(drama);


            System.out.println(" Dane startowe (Gatunki) załadowane pomyślnie.\n");
        } else {
            System.out.println(" Dane już istnieją — pomijam inicjalizację.\n");
        }
    }
}