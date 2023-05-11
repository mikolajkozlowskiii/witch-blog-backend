package com.example.witchblog.repositories.tarot;

import com.example.witchblog.entity.tarot.TarotCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarotCardRepository extends JpaRepository<TarotCard, Long> {
    Optional<TarotCard> findById(Long id);
    Optional<TarotCard> findByName(String name);
    List<TarotCard> findAll();
}
