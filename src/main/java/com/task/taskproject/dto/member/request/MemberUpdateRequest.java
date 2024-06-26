package com.task.taskproject.dto.member.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author : 최대준
 * @ClassName : MemberUpdateRequest
 * @Description : 회원 수정 Request 정보
 * @Since : 2024. 06. 24.
 */
public record MemberUpdateRequest(
        @Schema(description = "기존 비밀번호", example = "1234")
        String password,
        @Schema(description = "새 비밀번호", example = "1234")
        String newPassword,
        @Schema(description = "이름", example = "최대준")
        String name,
        @Schema(description = "주민등록번호", example = "990424-1XXXXXX")
        String regNo
) {
}
