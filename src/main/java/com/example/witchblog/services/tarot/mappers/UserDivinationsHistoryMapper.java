package com.example.witchblog.services.tarot.mappers;

import com.example.witchblog.dto.tarot.response.UserDivinationsHistoryResponse;
import com.example.witchblog.entity.tarot.TarotCard;
import com.example.witchblog.entity.divination.DivinationUserHistory;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDivinationsHistoryMapper {
    public UserDivinationsHistoryResponse map(DivinationUserHistory historyEntity){
        return UserDivinationsHistoryResponse
                .builder()
                .id(historyEntity.getId())
               // .tarotCardsName(historyEntity.getCards().stream().map(TarotCard::getName).collect(Collectors.toSet()))
                .userId(historyEntity.getUser().getId())
                .createdAt(historyEntity.getCreatedAt())
                .build();
    }
}
