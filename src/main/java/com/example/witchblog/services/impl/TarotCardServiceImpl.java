package com.example.witchblog.services.impl;

import com.example.witchblog.exceptions.CardNotFoundException;
import com.example.witchblog.models.tarot.TarotCard;
import com.example.witchblog.repositories.TarotCardRepository;
import com.example.witchblog.services.TarotCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
