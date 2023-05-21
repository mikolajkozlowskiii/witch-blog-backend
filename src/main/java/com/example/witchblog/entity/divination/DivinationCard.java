package com.example.witchblog.entity.divination;

import com.example.witchblog.entity.tarot.TarotCard;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "card_divinations")
public class DivinationCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private TarotCard card;
    private boolean isReversed;
    private String meaning;
}
