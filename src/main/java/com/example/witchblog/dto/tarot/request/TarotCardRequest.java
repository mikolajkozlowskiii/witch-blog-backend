package com.example.witchblog.dto.tarot.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarotCardRequest {
    private String nameOfCard;
    private boolean isReversed;
}
