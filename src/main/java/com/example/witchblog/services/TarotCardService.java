package com.example.witchblog.services;

import com.example.witchblog.models.tarot.TarotCard;

import java.util.List;

public interface TarotCardService {
    TarotCard  save(TarotCard cards);
    List<TarotCard>  save(List<TarotCard> cards);
    List<TarotCard> getRandomCards(int numOfCards);
    TarotCard findCardById(Long id);
    TarotCard findCardByName(String name);
    List<TarotCard> findAll();

}
