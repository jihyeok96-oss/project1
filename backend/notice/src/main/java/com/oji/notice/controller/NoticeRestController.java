package com.oji.notice.controller;

import com.oji.notice.dto.NoticeRequest;
import com.oji.notice.dto.NoticeResponse;
import com.oji.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeRestController {
    private final NoticeService noticeService;

    //생성: 201 => HttpStatus.CREATED
    //삭제: 204 => HttpStatus.NO_CONTENT
    //조회/수정: 200 => HttpStatus.OK

    //목록
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<NoticeResponse> list() {
        return noticeService.findAll();
    }

    //글 상세보기
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoticeResponse detail(@PathVariable("id") Long id) {
        return noticeService.findById(id);
    }

    //글 등록
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public NoticeResponse create(@Valid @RequestBody NoticeRequest request) {
        return noticeService.create(request);
    }

    //글 수정
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoticeResponse update(@PathVariable("id") Long id,
                                 @Valid @RequestBody NoticeRequest request) {
        return noticeService.update(id, request);
    }
    //글 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        noticeService.delete(id);
    }
}
