package com.example.witchblog.services.tarot.mappers;

import com.example.witchblog.dto.response.TarotCardResponse;
import com.example.witchblog.entity.tarot.TarotCard;
import org.springframework.stereotype.Component;

@Component
public class TarotMapper {
    public TarotCardResponse map(TarotCard tarotCard){
        return TarotCardResponse.builder()
                .id(tarotCard.getId())
                .img(tarotCard.getImg())
                .number(tarotCard.getNumber())
                .suit(tarotCard.getSuit())
                .arcana(tarotCard.getArcana())
                .name(tarotCard.getName())
                .build();
    }
}
