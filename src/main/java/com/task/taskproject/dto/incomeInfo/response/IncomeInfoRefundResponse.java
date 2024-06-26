package com.task.taskproject.dto.incomeInfo.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author : 최대준
 * @ClassName : IncomeInfoRefundResponse
 * @Description : 결정세액 Response 정보
 * @Since : 2024. 06. 26.
 */
public record IncomeInfoRefundResponse(
        @Schema(description = "결정세액", example = "150,000")
        String 결정세액
) {
}
