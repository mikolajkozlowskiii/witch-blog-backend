package com.example.witchblog.services.divination;

import com.example.witchblog.entity.divination.DivinationCard;
import com.example.witchblog.entity.tarot.TarotCard;

import java.util.Set;

public interface DivinationCardService{
    Set<DivinationCard> generateDivinationCards();
}
