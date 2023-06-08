package com.example.witchblog.services.divination.impl;

import com.example.witchblog.dto.tarot.request.TarotCardRequest;
import com.example.witchblog.entity.divination.DivinationCard;
import com.example.witchblog.entity.tarot.TarotCard;
import com.example.witchblog.services.divination.DivinationCardService;
import com.example.witchblog.services.tarot.TarotCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DivinationCardServiceImpl implements DivinationCardService {
    private final TarotCardService tarotCardService;
    @Override
    public Set<DivinationCard> generateDivinationCards() {
        // TODO REPLACE LIST TO SET IN TAROTCARDSERVICE
        List<TarotCard> tarotCards = tarotCardService.getRandomCards(3);
        return tarotCards.stream().map(this::generateDivinationCard).collect(Collectors.toSet());
    }

    @Override
    public Set<DivinationCard> generateDivinationCards(Set<TarotCardRequest> tarotCardRequests) {
        return tarotCardRequests.stream().map(this::generateDivinationCard).collect(Collectors.toSet());
    }

    private DivinationCard generateDivinationCard(TarotCard tarotCard){
        final boolean isReversed = getRandomBoolean();
        final String meaning = getRandomMeaning(tarotCard, isReversed);
        return DivinationCard.builder()
                .card(tarotCard)
                .isReversed(isReversed)
                .meaning(meaning)
                .build();
    }
    private DivinationCard generateDivinationCard(TarotCardRequest cardRequest){
        final boolean isReversed = cardRequest.isReversed();
        final TarotCard tarotCard = tarotCardService.findCardByName(cardRequest.getNameOfCard());
        final String meaning = getRandomMeaning(tarotCard, isReversed);
        return DivinationCard.builder()
                .card(tarotCard)
                .isReversed(isReversed)
                .meaning(meaning)
                .build();
    }

    private boolean getRandomBoolean(){
        return new Random().nextBoolean();
    }

    private String getRandomMeaning(TarotCard tarotCard, boolean isReversed) {
        final Set<String> potentialMeanings =
                isReversed ? tarotCard.getMeanings().getShadow() : tarotCard.getMeanings().getLight();
        return potentialMeanings.stream()
                .skip(new Random().nextInt(potentialMeanings.size()))
                .findFirst()
                .orElse(null);
        // TODO exception thrown
    }
}
