package com.example.witchblog.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DivinationCardResponse {
    private TarotCardResponse card;
    private boolean isReversed;
    private String meaning;
}
