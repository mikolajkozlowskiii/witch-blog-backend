package com.example.witchblog.entity.articles;

import com.example.witchblog.entity.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String title;
    @Lob
    @Column(name="CONTENT", length=5000)
    private String content;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    @ManyToOne
    private User createdBy;

    @JoinColumn(name = "modified_by")
    @ManyToOne
    private User modifiedBy;
}
