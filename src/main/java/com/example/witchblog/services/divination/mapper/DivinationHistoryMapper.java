package com.example.witchblog.services.divination.mapper;

import com.example.witchblog.dto.response.DivinationUserHistoryResponse;
import com.example.witchblog.entity.divination.DivinationUserHistory;
import com.example.witchblog.services.users.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DivinationHistoryMapper {
    private final UserMapper userMapper;
    private final DivinationMapper divinationMapper;
    public DivinationUserHistoryResponse map(DivinationUserHistory history){

        return DivinationUserHistoryResponse.builder()
                .id(history.getId())
                .createdAt(history.getCreatedAt())
                .divinationResponse(divinationMapper.map(history.getDivination()))
                .userResponse(userMapper.map(history.getUser()))
                .build();
    }
}
