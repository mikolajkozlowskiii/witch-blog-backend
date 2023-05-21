package com.example.witchblog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
@Data
@Builder
public class DivinationResponse {
    private Set<DivinationCardResponse> cardsResponse;
    private String prediction;
}
