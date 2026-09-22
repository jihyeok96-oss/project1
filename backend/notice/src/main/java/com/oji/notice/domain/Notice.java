package com.oji.notice.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="notices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id                                                     // => Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // => 값이 자동 증가
    private long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    @Builder.Default
    private String author = "admin";

    @Column(nullable = false)
    @Builder.Default
    private Long hits = 0L;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if(createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if(hits == null) {
            hits = 0L;
        }

        if(author == null) {
            author = "admin";
        }
    }
}
