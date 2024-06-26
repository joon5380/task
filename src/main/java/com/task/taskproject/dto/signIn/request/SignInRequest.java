package com.task.taskproject.dto.signIn.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author : 최대준
 * @ClassName : SignInRequest
 * @Description : 로그인 Request 정보
 * @Since : 2024. 06. 25.
 */
public record SignInRequest(
        @Schema(description = "아이디", example = "user1234")
        String userId,
        @Schema(description = "비밀번호", example = "1234")
        String password
) {
}
