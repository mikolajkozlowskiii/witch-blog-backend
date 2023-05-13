package com.example.witchblog.dto.tarot.response;

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
    private Set<String> tarotCardsName;
    private Long userId;
    private Instant createdAt;
}
