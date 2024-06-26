package com.task.taskproject.service;

import com.task.taskproject.dto.incomeInfo.response.IncomeInfoRefundResponse;

/**
 * @Author : 최대준
 * @InterfaceName : SzsService
 * @Description : 스크래핑 및 결정세액 API 구현체 인터페이스
 * @Since : 2024. 06. 25.
 */
public interface SzsService {
    String postScrap(String userId);
    IncomeInfoRefundResponse postRefund(String userId);
}
