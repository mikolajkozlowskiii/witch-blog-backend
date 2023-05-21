package com.example.witchblog.entity.divination;

import com.example.witchblog.entity.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(	name = "divinations_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DivinationUserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Divination divination;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private User user;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
