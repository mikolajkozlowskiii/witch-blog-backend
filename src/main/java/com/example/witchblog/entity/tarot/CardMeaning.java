package com.example.witchblog.entity.tarot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@NamedEntityGraph(name = "CardMeaning.withSets", attributeNodes = {
        @NamedAttributeNode("light"),
        @NamedAttributeNode("shadow")
})
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
    private Set<String> light;
    @ElementCollection
    private Set<String> shadow;

}






