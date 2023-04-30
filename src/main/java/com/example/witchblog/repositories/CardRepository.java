package com.example.witchblog.repositories;

import com.example.witchblog.models.tarot.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
}
