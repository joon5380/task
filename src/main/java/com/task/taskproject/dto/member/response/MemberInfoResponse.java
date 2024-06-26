package com.task.taskproject.dto.member.response;

import com.task.taskproject.common.MemberType;
import com.task.taskproject.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * @Author : 최대준
 * @ClassName : MemberInfoResponse
 * @Description : 회원 정보 Response 정보
 * @Since : 2024. 06. 24.
 */
public record MemberInfoResponse(
        @Schema(description = "아이디", example = "user1234")
        String userId,
        @Schema(description = "이름", example = "동탁")
        String name,
        @Schema(description = "주민등록번호", example = "921108-1582816")
        String regNo,
        @Schema(description = "신분 구분", example = "USER")
        MemberType type,
        @Schema(description = "회원가입 일시", example = "2023-05-11T15:00:00")
        LocalDateTime createdAt
) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getUserId(),
                member.getName(),
                member.getRegNo(),
                member.getType(),
                member.getCreatedAt()
        );
    }
}
