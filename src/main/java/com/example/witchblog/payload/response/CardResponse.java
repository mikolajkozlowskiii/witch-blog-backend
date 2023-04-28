package com.example.witchblog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@ToString
public class CardResponse {
    private String name;
    private String type;
    private byte[] image;
}
