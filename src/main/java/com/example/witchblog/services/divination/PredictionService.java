package com.example.witchblog.services.divination;

import com.example.witchblog.entity.divination.DivinationCard;

import java.io.IOException;
import java.util.Set;

public interface PredictionService {
    public String generatePrediction(String userName, Set<DivinationCard> divinationCards) throws IOException;
}
