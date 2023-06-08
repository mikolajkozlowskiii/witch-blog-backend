package com.example.witchblog.services.divination.impl;

import com.example.witchblog.dto.response.DivinationResponse;
import com.example.witchblog.dto.response.DivinationUserHistoryResponse;
import com.example.witchblog.dto.tarot.request.TarotCardRequest;
import com.example.witchblog.entity.divination.Divination;
import com.example.witchblog.entity.divination.DivinationCard;
import com.example.witchblog.entity.divination.DivinationUserHistory;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.divination.DivinationCardService;
import com.example.witchblog.services.divination.DivinationService;
import com.example.witchblog.services.divination.DivinationUserHistoryService;
import com.example.witchblog.services.divination.mapper.DivinationHistoryMapper;
import com.example.witchblog.services.divination.mapper.DivinationMapper;
import com.example.witchblog.services.divination.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class DivinationServiceImpl implements DivinationService {
    private final DivinationCardService divinationCardService;
    private final DivinationUserHistoryService divinationUserHistoryService;
    @Qualifier("localDivinationGenerator")
    private final PredictionService predictionService;
    private final DivinationMapper divinationMapper;
    private final DivinationHistoryMapper divinationHistoryMapper;
    @Override
    public DivinationResponse makeUserDivination(UserDetailsImpl currentUser) throws IOException {
        final Divination divination = generateDivinationEntity(currentUser.getName());
        divinationUserHistoryService.save(divination, currentUser);
        return divinationMapper.map(divination);
    }

    @Override
    public DivinationResponse makeUserDivinationForMobileApp(UserDetailsImpl currentUser,
                                                             Set<TarotCardRequest> cards) throws IOException {
        final Divination divination = generateDivinationEntity(cards, currentUser.getName());
        divinationUserHistoryService.save(divination, currentUser);
        return divinationMapper.map(divination);
    }

    @Override
    public DivinationResponse makeDivinationAnonymoulsy(Set<TarotCardRequest> cards) throws IOException {
        final Divination divination = generateDivinationEntity(cards);
        return divinationMapper.map(divination);
    }
    private Divination generateDivinationEntity(Set<TarotCardRequest> cards) throws IOException {
        final Set<DivinationCard> divinationCards = divinationCardService.generateDivinationCards(cards);
        final String prediction = predictionService.generatePredictionAnonymously(divinationCards);
        return Divination.builder()
                .cards(divinationCards)
                .prediction(prediction)
                .build();
    }
    private Divination generateDivinationEntity(Set<TarotCardRequest> cards, String userName) throws IOException {
        final Set<DivinationCard> divinationCards = divinationCardService.generateDivinationCards(cards);
        final String prediction = predictionService.generatePrediction(userName, divinationCards);
        return Divination.builder()
                .cards(divinationCards)
                .prediction(prediction)
                .build();
    }
    private Divination generateDivinationEntity(String userName) throws IOException {
        final Set<DivinationCard> divinationCards = divinationCardService.generateDivinationCards();
        final String prediction = predictionService.generatePrediction(userName, divinationCards);
        return Divination.builder()
                .cards(divinationCards)
                .prediction(prediction)
                .build();
    }

    @Override
    public Page<DivinationUserHistoryResponse> getUserDivinations(UserDetailsImpl currentUser, Pageable pageable) {
        final Page<DivinationUserHistory> history = divinationUserHistoryService.findAllByUserId(currentUser.getId(), pageable);
        final List<DivinationUserHistoryResponse> historyResponse = history.stream().map(divinationHistoryMapper::map).toList();
        return new PageImpl<DivinationUserHistoryResponse>(historyResponse);
    }
}


/*
    private String generateGeneralDivination(String userName, Set<DivinationCard> divinationCards){
        return
       Set<String> meanings = divinationCards.stream().map(DivinationCard::getMeaning).collect(Collectors.toSet());
        StringBuilder question = new StringBuilder("Write a magic and astrology    divination for \"" + name + "\". Use these sentences: ");
        for(String meaning : meanings) question.append(meaning).append(", ");
        String divinationContent = openAIService.chat(question.toString());
        return divinationContent;
    }*/
