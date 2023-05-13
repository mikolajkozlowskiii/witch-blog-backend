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
    // jaka metoda ktora jako argument pobiera @currentUserDetails czy cos i w nim sie zapisuje saveuser divination i sa zwrocone tez te larty 3
    // pobiera numOfCards i currentUser, w body te 3 karty + save wylosowania, zwraca te 3 karty

}
