package com.example.witchblog.email.entity;

import com.example.witchblog.entity.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    @Column(nullable = true)
    private LocalDateTime confirmedAt;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(
            nullable = true,
            name = "user_id"
    )
    private User user;
}
