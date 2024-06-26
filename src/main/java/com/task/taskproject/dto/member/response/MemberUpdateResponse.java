package com.task.taskproject.dto.member.response;

import com.task.taskproject.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author : 최대준
 * @ClassName : MemberUpdateResponse
 * @Description : 회원 수정 Response 정보
 * @Since : 2024. 06. 25.
 */
public record MemberUpdateResponse(
        @Schema(description = "수정 성공 여부", example = "true")
        boolean result,
        @Schema(description = "이름", example = "최대준")
        String name,
        @Schema(description = "주민등록번호", example = "990424-1234567")
        String regNo
) {
    public static MemberUpdateResponse of(boolean result, Member member) {
        return new MemberUpdateResponse(result, member.getName(), member.getRegNo());
    }
}
