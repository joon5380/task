package com.task.taskproject.dto.incomeInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.taskproject.entity.MemberIncmInfoCard;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

/**
 * @Author : 최대준
 * @ClassName : IncomeInfoCardResponse
 * @Description : 신용카드소득공제 Response 정보
 * @Since : 2024. 06. 25.
 */
public record IncomeInfoCardResponse(
        @Schema(description = "사용자 아이디", example = "user1234")
        String userId,
        @Schema(description = "연도", example = "2023")
        @JsonProperty("year")
        String year,
        @JsonProperty("month")
        List<Map<String, String>> cardIncomeList, // 신용카드 소득공제 월(cardMonth), 공제액(deductible) 목록
        @Schema(description = "월", example = "01")
        String cardMonth,
        @Schema(description = "공제액", example = "100000.10")
        double deductible
) {
    public MemberIncmInfoCard from(String id, Map<String, String> cardIncomeMap) {
        String mMonth = "", mDeductible = "";
        for (Map.Entry<String, String> entrySet : cardIncomeMap.entrySet()) {
            mMonth = entrySet.getKey();
            mDeductible = entrySet.getValue();
        }
        return MemberIncmInfoCard.builder()
                .userId(id)
                .deductYear(year)
                .deductMonth(mMonth)
                .deductible(Double.parseDouble(mDeductible.replaceAll(",", "")))
                .build();
    }
}