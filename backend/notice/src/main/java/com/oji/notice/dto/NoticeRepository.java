package com.oji.notice.dto;

import com.oji.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository <Notice, Long> {

    @Modifying
    @Query("UPDATE Notice n SET n.hits = n.hits + 1 WHERE n.id = :id")
    int increaseHits(@Param("id") Long id);
}
