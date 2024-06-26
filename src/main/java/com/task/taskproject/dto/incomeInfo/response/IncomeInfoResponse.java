package com.task.taskproject.dto.incomeInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.taskproject.entity.MemberIncmInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author : 최대준
 * @ClassName : IncomeInfoResponse
 * @Description : 사용자 소득 및 공제정보 Response 정보
 * @Since : 2024. 06. 25.
 */
public record IncomeInfoResponse(
        @Schema(description = "사용자 아이디", example = "user1234")
        String userId,
        @Schema(description = "이름", example = "동탁")
        @JsonProperty("이름")
        String name,
        @Schema(description = "종합소득금액", example = "20000000")
        @JsonProperty("종합소득금액")
        String totalIncmAmt,
        @JsonProperty("소득공제")
        Deductions deductions
) {
    @Data
    public static class Deductions {
        @JsonProperty("국민연금")
        private List<IncomeInfoPensionResponse> pensionDeductions;

        @JsonProperty("신용카드소득공제")
        private IncomeInfoCardResponse creditCardDeduction;

        @JsonProperty("세액공제")
        private String taxDeduction;
    }

    public MemberIncmInfo of(String id) {
        return MemberIncmInfo.builder()
                .userId(id)
                .name(name)
                .totalIncmAmt(Double.parseDouble(totalIncmAmt.replaceAll(",", "")))
                .taxDeduction(Double.parseDouble(deductions.getTaxDeduction().replaceAll(",", "")))
                .build();
    }
}
