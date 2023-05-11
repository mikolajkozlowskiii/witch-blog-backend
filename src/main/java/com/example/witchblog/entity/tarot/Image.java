package com.example.witchblog.entity.tarot;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(	name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "image", unique = false, nullable = false, length = 100000)
    private byte[] image;
}
