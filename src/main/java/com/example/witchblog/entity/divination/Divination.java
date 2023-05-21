package com.example.witchblog.entity.divination;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "divinations")
public class Divination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "divinationCardId")
    private Set<DivinationCard> cards;
    @Lob
    @Column(name="CONTENT", length=512)
    private String prediction;
}
