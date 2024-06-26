package com.task.taskproject.dto.signUp.response;

import com.task.taskproject.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author : 최대준
 * @ClassName : SignUpResponse
 * @Description : 회원가입 Response 정보
 * @Since : 2024. 06. 25.
 */
public record SignUpResponse(
        @Schema(description = "아이디", example = "user1234")
        String userId,
        @Schema(description = "회원 이름", example = "동탁")
        String name,
        @Schema(description = "주민등록번호", example = "921108-1582816")
        String regNo
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
                member.getUserId(),
                member.getName(),
                member.getRegNo() // 복호화 없이 데이터 처리
        );
    }
}
