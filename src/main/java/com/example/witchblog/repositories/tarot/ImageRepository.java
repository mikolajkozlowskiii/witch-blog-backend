package com.example.witchblog.repositories.tarot;

import com.example.witchblog.entity.tarot.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
}
