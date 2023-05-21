package com.example.witchblog.services.divination.impl;

import com.example.witchblog.components.divination.Sentence;
import com.example.witchblog.components.divination.SentencesBuilder;
import com.example.witchblog.entity.divination.DivinationCard;
import com.example.witchblog.services.divination.PredictionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Qualifier("localDivinationGenerator")
public class LocalPredictionServiceImpl implements PredictionService {
    @Value("classpath:sentences.json")
    private Resource resourceFile;
    private final SentencesBuilder sentencesBuilder;

    @Override
    public String generatePrediction(String userName, Set<DivinationCard> divinationCards) {
        final Sentence sentence = loadSentences();

        final String prediction = sentencesBuilder.getSentencesOfPrediction(userName, sentence, divinationCards);
        return prediction;
    }

    @Transactional
    private Sentence loadSentences() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = resourceFile.getInputStream();
            Sentence sentence = objectMapper.readValue(inputStream, Sentence.class);
            return sentence;
        } catch (IOException ex) {
            return null;
        }
    }

}


