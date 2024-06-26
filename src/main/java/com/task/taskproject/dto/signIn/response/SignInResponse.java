package com.task.taskproject.dto.signIn.response;

import com.task.taskproject.common.MemberType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author : 최대준
 * @ClassName : SignInResponse
 * @Description : 로그인 Response 정보
 * @Since : 2024. 06. 25.
 */
public record SignInResponse(
        @Schema(description = "이름", example = "최대준")
        String name,
        @Schema(description = "신분 구분", example = "USER")
        MemberType type,
        String accessToken
) {
}
