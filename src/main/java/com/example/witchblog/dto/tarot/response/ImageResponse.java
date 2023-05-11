package com.example.witchblog.dto.tarot.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@ToString
public class ImageResponse {
    private String name;
    private String type;
    private byte[] image;
}
