package com.example.witchblog.dto.response;

import com.example.witchblog.dto.users.response.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
@Data
@Builder
public class DivinationUserHistoryResponse {
    private Long id;
    private DivinationResponse divinationResponse;
    private UserResponse userResponse;
    private Instant createdAt;
}
