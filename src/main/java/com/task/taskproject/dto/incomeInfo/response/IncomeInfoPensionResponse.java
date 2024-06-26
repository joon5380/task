package com.task.taskproject.dto.incomeInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.taskproject.entity.MemberIncmInfoPension;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author : 최대준
 * @ClassName : IncomeInfoPensionResponse
 * @Description : 국민연금소득공제 Response 정보
 * @Since : 2024. 06. 25.
 */
public record IncomeInfoPensionResponse(
        @Schema(description = "사용자 아이디", example = "user1234")
        String userId,
        @Schema(description = "연도", example = "2023")
        String year,
        @Schema(description = "월", example = "01")
        @JsonProperty("월")
        String pensionMonth,
        @Schema(description = "공제액", example = "200000.10")
        @JsonProperty("공제액")
        String deductible
) {
    public MemberIncmInfoPension of(String id) {
        String[] parts = pensionMonth.split("-");
        return MemberIncmInfoPension.builder()
                .userId(id)
                .deductYear(parts[0]) // 국민연금 공제연도
                .deductMonth(parts[1]) // 국민연금 공제월
                .deductible(Double.parseDouble(deductible.replaceAll(",", "")))
                .build();
    }
}
