// org.example.elementservice.repository.GenreRepository.java

package org.example.elementservice.repository;

import org.example.elementservice.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
    
}