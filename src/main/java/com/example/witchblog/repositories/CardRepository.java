package com.example.witchblog.repositories;

import com.example.witchblog.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByName(String name);
}
