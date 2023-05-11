package com.example.witchblog.entity.tarot;

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

}






