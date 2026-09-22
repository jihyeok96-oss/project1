package com.kim.notice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    //400 => HttpStatus.BAD_REQUEST: 입력값(파라미터값) 잘못됨 => MethodArgumentNotValidException
    //401 => HttpStatus.UNAUTHORIZED: 권한 없음(로그인이 안된 상태)
    //403 => HttpStatus.FORBIDDEN: 인가는 되었으나 권한이 없는 경우
    //404 => HttpStatus.NOT_FOUND: 잘못된 URL 요청
    //405 => HttpStatus.METHOD_NOT_ALLOWED: 요청방식이 틀림.
    //500 => HttpStatus.INTERNAL_SEVER_ERROR: 핸들링이 되지 않는 모든 예외

    //400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(field -> field.getDefaultMessage())
                .orElse("입력값을 확인해주세요");
        return error(HttpStatus.BAD_REQUEST.value(), message); //400, "입력값을 확인해주세요"
    }

    //404 http://localhost:8081/api/notice/1
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleIllegalArgument(IllegalArgumentException e) {
        return error(HttpStatus.NOT_FOUND.value(), e.getMessage()); //404, "기본 메시지"
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류가 발생하였습니다."); //500,  "서버 오류가 발생하였습니다."
    }

    //Java의 Map => JSON으로 내보내기
    public Map<String, Object> error(int status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status); //
        body.put("message", message);
            /*
            {
                "status": 400,
                "message": "입력값을 확인해주세요"
             }
            */
        return body;
    }
}