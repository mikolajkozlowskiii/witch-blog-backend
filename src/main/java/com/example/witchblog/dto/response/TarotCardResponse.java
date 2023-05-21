package com.example.witchblog.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TarotCardResponse {
    private Long id;
    private String name;
    private String number;
    private String arcana;
    private String suit;
    private String img;
}
