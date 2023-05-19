package com.example.witchblog.services.tarot;

import com.example.witchblog.entity.tarot.TarotCard;
import com.example.witchblog.security.userDetails.UserDetailsImpl;

import java.util.List;

public interface TarotCardService {
    TarotCard  save(TarotCard cards);
    List<TarotCard>  save(List<TarotCard> cards);
    List<TarotCard> getRandomCards(int numOfCards);
    List<TarotCard> generateDivinationForUser(UserDetailsImpl currentUser, int numOfCards);
    TarotCard findCardById(Long id);
    TarotCard findCardByName(String name);
    List<TarotCard> findAll();
}
