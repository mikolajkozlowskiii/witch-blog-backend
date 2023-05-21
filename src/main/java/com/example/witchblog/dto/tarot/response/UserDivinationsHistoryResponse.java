package com.example.witchblog.dto.tarot.response;

import com.example.witchblog.dto.response.DivinationResponse;
import com.example.witchblog.entity.divination.Divination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@ToString
public class UserDivinationsHistoryResponse {
    private Long id;
    private DivinationResponse divinationResponse;
    private Long userId;
    private Instant createdAt;
}
