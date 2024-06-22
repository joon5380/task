package com.task.taskproject.controller;

import com.task.taskproject.entity.Member;
import com.task.taskproject.service.SzsService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SzsController {

    // 서버 로그 추가?

    @Resource(name = "SzsService")
    private SzsService szsService;

    @PostMapping(value = "/szs/signup")
    public ResponseEntity<?> test(
        @RequestBody Map<String, Object> content // DTO 객체로 할까? 말까?
    )
    {
        if (content != null) {
            String result = szsService.test(content);
            // 정상 처리 로직
        } else {
            // 응답 코드 생성
        }

        return ResponseEntity.status(HttpStatus.OK).body("okay!!");
    }

}
