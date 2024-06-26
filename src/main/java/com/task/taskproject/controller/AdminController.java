package com.task.taskproject.controller;

import com.task.taskproject.controller.AdminController.API;
import com.task.taskproject.dto.ApiResponse;
import com.task.taskproject.security.AdminAuthorize;
import com.task.taskproject.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 최대준
 * @ClassName : AdminController
 * @Description : 관리자용 API 컨트롤러
 * @Since : 2024. 06. 24.
 */
@Tag(name = "관리자용 API")
@AdminAuthorize // 커스텀 어노테이션 관리자 API 검증
@RestController
@RequestMapping(API.BASE)
public class AdminController {
    public static class API {
        public static final String BASE = "/admin";
        public static final String BASE_PATTERN = BASE + "/**";

        public static final String GET_USER_LIST = "/user-list";
        public static final String GET_ADMIN_LIST = "/admin-list";
    }

    @Resource(name = "AdminService")
    private AdminService adminService;

    /**
     * @Program Name : getAllUserList
     * @Describe : 회원목록조회 API
     * @Date : 2024. 06. 24.
     * @Writer : 최대준
     * @History : 2024. 06. 24. | 최대준 | 최초작성
     */
    @Operation(summary = "회원 목록 조회")
    @GetMapping(API.GET_USER_LIST)
    public ApiResponse getAllUserList() {
        return ApiResponse.success(adminService.getUserList());
    }

    /**
     * @Program Name : getAllAdminList
     * @Describe : 관리자목록조회 API
     * @Date : 2024. 06. 24.
     * @Writer : 최대준
     * @History : 2024. 06. 24. | 최대준 | 최초작성
     */
    @Operation(summary = "관리자 목록 조회")
    @GetMapping(API.GET_ADMIN_LIST)
    public ApiResponse getAllAdminList() {
        return ApiResponse.success(adminService.getAdminList());
    }
}
