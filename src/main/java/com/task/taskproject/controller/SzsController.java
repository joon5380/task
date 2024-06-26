package com.task.taskproject.controller;

import com.task.taskproject.controller.SzsController.API;
import com.task.taskproject.dto.ApiResponse;
import com.task.taskproject.security.UserAuthorize;
import com.task.taskproject.service.SzsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 최대준
 * @ClassName : SzsController
 * @Description : 스크래핑 및 결정세액 API 컨트롤러
 * @Since : 2024. 06. 25.
 */
@Tag(name = "스크래핑 및 결정세액 조회 API")
@UserAuthorize // 커스텀 어노테이션 일반 사용자 API 검증
@RestController
@RequestMapping(API.BASE)
public class SzsController {
    public static class API {
        public static final String BASE = "/szs";
        public static final String BASE_PATTERN = BASE + "/**";
        public static final String POST_SCRAP = "/scrap";
        public static final String POST_REFUND = "/refund";
    }

    @Resource(name = "SzsService")
    private SzsService szsService;

    /**
     * @Program Name : postScrap
     * @Describe : 스크래핑 API
     * @Param : User(user) 로그인한 사용자 정보
     * @Date : 2024. 06. 25.
     * @Writer : 최대준
     * @History : 2024. 06. 25. | 최대준 | 최초작성
     */
    @Operation(summary = "스크래핑 조회")
    @PostMapping(API.POST_SCRAP)
    public ApiResponse postScrap(
        @AuthenticationPrincipal User user
    ) {
        return ApiResponse.success(szsService.postScrap(user.getUsername()));
    }

    /**
     * @Program Name : postRefund
     * @Describe : 결정세액 조회 API
     * @Param1 : User(user) 로그인한 사용자 정보
     * @Date : 2024. 06. 25.
     * @Writer : 최대준
     * @History : 2024. 06. 25. | 최대준 | 최초작성
     */
    @Operation(summary = "결정세액 조회")
    @PostMapping(API.POST_REFUND)
    public ApiResponse postRefund(
        @AuthenticationPrincipal User user
    ) {
        return ApiResponse.success(szsService.postRefund(user.getUsername()));
    }

}
