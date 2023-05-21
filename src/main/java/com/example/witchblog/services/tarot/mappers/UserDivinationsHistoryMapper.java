package com.example.witchblog.services.tarot.mappers;

import com.example.witchblog.dto.tarot.response.UserDivinationsHistoryResponse;
import com.example.witchblog.entity.tarot.TarotCard;
import com.example.witchblog.entity.divination.DivinationUserHistory;
import com.example.witchblog.services.divination.mapper.DivinationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDivinationsHistoryMapper {
    private final DivinationMapper divinationMapper;
    public UserDivinationsHistoryResponse map(DivinationUserHistory historyEntity){
        return UserDivinationsHistoryResponse
                .builder()
                .id(historyEntity.getId())
                .divinationResponse(divinationMapper.map(historyEntity.getDivination()))
                .userId(historyEntity.getUser().getId())
                .createdAt(historyEntity.getCreatedAt())
                .build();
    }
}
