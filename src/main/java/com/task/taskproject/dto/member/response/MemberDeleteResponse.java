package com.task.taskproject.dto.member.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author : 최대준
 * @ClassName : MemberDeleteResponse
 * @Description : 회원 탈퇴 Response 정보
 * @Since : 2024. 06. 24.
 */
public record MemberDeleteResponse(
        @Schema(description = "삭제 성공 여부", example = "true")
        boolean result
) {
}
