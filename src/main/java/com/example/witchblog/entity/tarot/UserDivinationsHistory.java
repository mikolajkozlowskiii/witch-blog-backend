package com.example.witchblog.entity.tarot;

import com.example.witchblog.entity.users.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Set;

@NamedEntityGraph(name = "divinations_history_graph",
        attributeNodes = {@NamedAttributeNode("cards"), @NamedAttributeNode("user")})
@Entity
@Table(	name = "divinations_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDivinationsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnoreProperties({"meanings", "anotherPropertyToIgnore", "keywords", "fortune_telling", "questionsToAsk"})
    @JsonProperty("tarotCardIds")
    private Set<TarotCard> cards;
    @OneToOne
    private User user;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
