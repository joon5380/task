package com.task.taskproject.dto.signUp.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author : 최대준
 * @ClassName : SignUpRequest
 * @Description : 회원가입 Request 정보
 * @Since : 2024. 06. 25.
 */
public record SignUpRequest(
        @Schema(description = "아이디", example = "user1234")
        String userId,
        @Schema(description = "비밀번호", example = "1234")
        String password,
        @Schema(description = "이름", example = "동탁")
        String name,
        @Schema(description = "주민등록번호", example = "921108-1582816")
        String regNo
) {
}
