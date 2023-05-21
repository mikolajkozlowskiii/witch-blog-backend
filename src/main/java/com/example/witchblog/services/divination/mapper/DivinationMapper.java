package com.example.witchblog.services.divination.mapper;

import com.example.witchblog.dto.response.DivinationCardResponse;
import com.example.witchblog.dto.response.DivinationResponse;
import com.example.witchblog.entity.divination.Divination;
import com.example.witchblog.entity.divination.DivinationCard;
import com.example.witchblog.services.tarot.mappers.TarotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DivinationMapper {
    private final TarotMapper tarotMapper;
    public DivinationResponse map(Divination divination){
        return DivinationResponse.builder()
                .prediction(Optional.ofNullable(divination.getPrediction()).orElse("divination not found"))
                .cardsResponse(divination.getCards().stream().map(this::map).collect(Collectors.toSet()))
                .build();
    }

    public DivinationCardResponse map(DivinationCard divinationCard){
        return DivinationCardResponse.builder()
                .meaning(divinationCard.getMeaning())
                .isReversed(divinationCard.isReversed())
                .card(tarotMapper.map(divinationCard.getCard()))
                .build();
    }
}
