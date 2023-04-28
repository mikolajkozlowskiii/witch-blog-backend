package com.example.witchblog.models;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(	name = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Card {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "image", unique = false, nullable = false, length = 100000)
    private byte[] image;
}
