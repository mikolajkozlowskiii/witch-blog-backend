package com.example.witchblog.entity.tarot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@NamedEntityGraph(name = "TarotCard.withCollections",
        attributeNodes = {
                @NamedAttributeNode("fortune_telling"),
                @NamedAttributeNode("keywords"),
                @NamedAttributeNode("questionsToAsk"),
                @NamedAttributeNode("meanings")
        }
)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tarot_cards")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarotCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private String number;

    @Column(name = "arcana")
    private String arcana;

    @Column(name = "suit")
    private String suit;

    @Column(name = "img")
    private String img;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<String> fortune_telling;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<String> keywords;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CardMeaning meanings;

    @JsonProperty("Archetype")
    @Column(name = "archetype")
    private String archetype;

    @JsonProperty("Hebrew Alphabet")
    @Column(name = "hebrew_alphabet")
    private String hebrewAlphabet;

    @JsonProperty("Numerology")
    @Column(name = "numerology")
    private String numerology;

    @JsonProperty("Elemental")
    @Column(name = "elemental")
    private String elemental;

    @JsonProperty("Mythical/Spiritual")
    @Column(name = "mythical_spiritual")
    private String mythicalSpiritual;

    @JsonProperty("Astrology")
    @Column(name = "astrology")
    private String astrology;

    @JsonProperty("Affirmation")
    @Column(name = "affirmation")
    private String affirmation;

    @JsonProperty("Questions to Ask")
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "question")
    @CollectionTable(name = "tarot_card_questions", joinColumns = @JoinColumn(name = "card_id"))
    private Set<String> questionsToAsk;

}