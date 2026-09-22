package com.oji.notice.dto;

import com.oji.notice.domain.Notice;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeResponse {

    private long id;
    private String title;
    private String content;
    private String author;
    private long hits;
    private LocalDateTime createdAt;

    //내부에서 Notice => NoticeResponse
    public static NoticeResponse from(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .author(notice.getAuthor())
                .hits(notice.getHits())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}
