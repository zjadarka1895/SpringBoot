package org.example.categoryservice.service;

import org.example.categoryservice.dto.genre.GenreDTO;
import org.example.categoryservice.model.Genre;
import org.example.categoryservice.repository.GenreRepository;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient; // 💡 Nowy import dla komunikacji REST
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final WebClient webClient;


private static final String MOVIE_SERVICE_EVENT_ENDPOINT = "http://movie-service:8082/api/events/genres";
    public GenreService(GenreRepository genreRepository, WebClient.Builder webClientBuilder) {
        this.genreRepository = genreRepository;
        // Konfiguracja WebClient z bazowym URL
        this.webClient = webClientBuilder.baseUrl(MOVIE_SERVICE_EVENT_ENDPOINT).build();
    }

    // =========================
    // ===== CRUD + EVENTY
    // =========================

    public Genre save(Genre genre) {
        boolean isNew = !genreRepository.existsById(genre.getId());

        Genre savedGenre = genreRepository.save(genre);

        sendGenreEvent(savedGenre, isNew ? "CREATE" : "UPDATE");

        return savedGenre;
    }

    public void deleteById(UUID id) {
        if (genreRepository.existsById(id)) {
            sendGenreDeleteEvent(id);

            genreRepository.deleteById(id);
        }
    }

    private void sendGenreEvent(Genre genre, String operation) {
        // Konwersja na DTO do wysłania
        GenreDTO eventDto = GenreDTO.builder()
                .id(genre.getId())
                .name(genre.getName())
                .popularityIndex(genre.getPopularityIndex())
                .build();

        // Asynchroniczne wywołanie REST (POST)
        webClient.post()
                .uri("")
                .bodyValue(eventDto)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Błąd REST w movie-service: " + response.statusCode())))
                .toBodilessEntity()
                .doOnError(e -> System.err.println("Błąd wysyłania eventu REST (" + operation + "): " + e.getMessage()))
                .subscribe();

        System.out.println("Wysłano event REST (" + operation + ") do movie-service dla ID: " + genre.getId());
    }

    /** Wysyła event do movie-service o usunięciu kategorii (Delete). */
    private void sendGenreDeleteEvent(UUID id) {
        // Asynchroniczne wywołanie REST (DELETE)
        webClient.delete()
                .uri("/" + id.toString()) 
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Błąd REST w movie-service: " + response.statusCode())))
                .toBodilessEntity()
                .doOnError(e -> System.err.println("Błąd wysyłania eventu REST (DELETE): " + e.getMessage()))
                .subscribe();

        System.out.println("Wysłano event REST (DELETE) do movie-service dla ID: " + id);
    }

    // =========================
    // ===== CRUD podstawowe
    // =========================

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Optional<Genre> findById(UUID id) {
        return genreRepository.findById(id);
    }

    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }


    public boolean existsById(UUID id) {
        return genreRepository.existsById(id);
    }

    public boolean existsByName(String name) {
        return genreRepository.findByName(name) != null;
    }

    // =========================
    // ===== DTO
    // =========================

    public List<GenreDTO> findAllDTO() {
        return genreRepository.findAll().stream()
                .map(g -> GenreDTO.builder()
                        .id(g.getId())
                        .name(g.getName())
                        .popularityIndex(g.getPopularityIndex())
                        .build())
                .toList();
    }
}