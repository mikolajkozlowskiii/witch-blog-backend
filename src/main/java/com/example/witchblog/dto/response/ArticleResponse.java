package com.example.witchblog.dto.response;

import com.example.witchblog.dto.users.response.UserResponse;
import com.example.witchblog.entity.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private UserResponse createdBy;
    private LocalDateTime modifiedDate;
    private UserResponse modifiedBy;
}
