package com.example.witchblog.repositories.tarot;

import com.example.witchblog.entity.tarot.TarotCard;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TarotCardRepository extends JpaRepository<TarotCard, Long> {
    Optional<TarotCard> findById(Long id);
    Optional<TarotCard> findByName(String name);

   // @Query("SELECT distinct t FROM TarotCard t LEFT JOIN FETCH t.meanings m")

    //@EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
      //     value = "TarotCard.withCollections")
   @Query("SELECT DISTINCT t FROM TarotCard t " +
           "LEFT JOIN FETCH t.fortune_telling " +
           "LEFT JOIN FETCH t.keywords " +
           "LEFT JOIN FETCH t.questionsToAsk " +
           "LEFT JOIN FETCH t.meanings " +
           "LEFT JOIN FETCH t.meanings.light " +
           "LEFT JOIN FETCH t.meanings.shadow")
    List<TarotCard> findAll();
}
