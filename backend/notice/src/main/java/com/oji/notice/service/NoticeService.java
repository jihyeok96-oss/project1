package com.oji.notice.service;

import com.oji.notice.domain.Notice;
import com.oji.notice.dto.NoticeRepository;
import com.oji.notice.dto.NoticeRequest;
import com.oji.notice.dto.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeRepository noticeRepository;

    //목록
    public List<NoticeResponse> findAll() {
        return noticeRepository.findAll(org.springframework.data.domain.Sort.by(
                Sort.Direction.DESC,"id"))
                .stream().map(NoticeResponse::from).toList();
    }

    //상세보기
    @Transactional
    public NoticeResponse findById(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("공지사항을 찾을 수 없습니다.")
        );
        //읽은 횟수 증가(NoticeRepository를 이용한 실제 DB, 엔티티 둘다)
        noticeRepository.increaseHits(id);
        notice.setHits(notice.getHits()+1);
         //Notice -> NoticeResponse
        return NoticeResponse.from(notice);
    }

    //글쓰기
    @Transactional
    public NoticeResponse create(NoticeRequest request) {
        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .build();
        return NoticeResponse.from(noticeRepository.save(notice));
    }

    //글 수정
    @Transactional
    public NoticeResponse update(Long id, NoticeRequest request) {
        Notice notice = noticeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(id + "번 글을 찾을 수 없습니다.")
        );
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setAuthor(request.getAuthor());
        return NoticeResponse.from(noticeRepository.save(notice));
        }

    //글 삭제
    @Transactional
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}