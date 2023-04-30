package com.example.witchblog.models.tarot;

import com.example.witchblog.models.tarot.TarotCard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "cards_meanings")
public class CardMeaning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @ElementCollection
    private List<String> light;
    @ElementCollection
    private List<String> shadow;
    /*
    @OneToOne(orphanRemoval = true)
    @JoinTable(name = "cards_meanings_tarotCard",
            joinColumns = @JoinColumn(name = "cardMeaning_null"),
            inverseJoinColumns = @JoinColumn(name = "tarotCard_id"))
    private TarotCard tarotCard;*/

}






