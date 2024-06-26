package com.task.taskproject.dto;

import com.task.taskproject.common.ApiStatus;

/**
 * @Author : 최대준
 * @ClassName : ApiResponse
 * @Description : API Response 객체 정보(success, error)
 * @Since : 2024. 06. 25.
 */
public record ApiResponse(
        ApiStatus code,
        String message,
        Object data
) {
    public static ApiResponse success(Object data) {
        return new ApiResponse(ApiStatus.SUCCESS, null, data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(ApiStatus.ERROR, message, null);
    }
}
