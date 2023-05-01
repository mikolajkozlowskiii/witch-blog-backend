package com.example.witchblog.services.impl;

import com.example.witchblog.exceptions.CardNotFoundException;
import com.example.witchblog.models.tarot.TarotCard;
import com.example.witchblog.repositories.TarotCardRepository;
import com.example.witchblog.services.TarotCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TarotCardServiceImpl implements TarotCardService {
    private TarotCardRepository tarotCardRepository;

    @Override
    public TarotCard save(TarotCard card) {
        return tarotCardRepository.save(card);
    }

    @Override
    public List<TarotCard> save(List<TarotCard> cards) {
        return tarotCardRepository.saveAll(cards);
    }

    @Override
    public List<TarotCard> getRandomCards(int numOfCards) {
        List<TarotCard> tarotCards = findAll();
        if (numOfCards <= 0 || numOfCards > tarotCards.size()) {
            throw new IllegalArgumentException("Invalid number of cards");
        }
        Collections.shuffle(tarotCards);

        return tarotCards.stream()
                .distinct()
                .limit(numOfCards)
                .collect(Collectors.toList());
    }

    @Override
    public TarotCard findCardById(Long id) {
        return tarotCardRepository
                .findById(id)
                .orElseThrow(() ->  new CardNotFoundException("Card id didnt found: " + id));
    }

    @Override
    public TarotCard findCardByName(String name) {
        return tarotCardRepository
                .findByName(name)
                .orElseThrow(() ->  new CardNotFoundException("Card name didnt found: " + name));
    }

    @Override
    public List<TarotCard> findAll() {
        return tarotCardRepository.findAll();
    }
}
