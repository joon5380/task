package com.task.taskproject.controller;

import com.task.taskproject.controller.AuthController.API;
import com.task.taskproject.dto.ApiResponse;
import com.task.taskproject.dto.signIn.request.SignInRequest;
import com.task.taskproject.dto.signUp.request.SignUpRequest;
import com.task.taskproject.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 최대준
 * @ClassName : AuthController
 * @Description : 회원가입 및 로그인 API 컨트롤러
 * @Since : 2024. 06. 21.
 */
@Tag(name = "회원가입 및 로그인")
@RestController
@RequestMapping(API.BASE)
public class AuthController {
    public static class API {
        public static final String BASE = "/szs";
        public static final String BASE_PATTERN = BASE + "/**";
        public static final String POST_SIGN_IN = "/sign-in";
        public static final String POST_SIGN_UP = "/sign-up";
    }

    @Resource(name = "AuthService")
    private AuthService authService;

    /**
     * @Program Name : signIn
     * @Describe : 로그인 API
     * @Param : SignInRequest(request) 로그인 정보[아이디, 비밀번호]
     * @Date : 2024. 06. 21.
     * @Writer : 최대준
     * @History : 2024. 06. 21. | 최대준 | 최초작성
     */
    @Operation(summary = "로그인")
    @PostMapping(API.POST_SIGN_IN)
    public ApiResponse signIn(
        @RequestBody SignInRequest request
    ) {
        return ApiResponse.success(authService.login(request));
    }

    /**
     * @Program Name : signUp
     * @Describe : 회원가입 API
     * @Param : SignUpRequest(request) 회원 정보[아이디, 비밀번호, 이름, 주민등록번호]
     * @Date : 2024. 06. 21.
     * @Writer : 최대준
     * @History : 2024. 06. 21. | 최대준 | 최초작성
     */
    @Operation(summary = "회원가입")
    @PostMapping(API.POST_SIGN_UP)
    public ApiResponse signUp(
        @RequestBody SignUpRequest request
    ) {
        return ApiResponse.success(authService.register(request));
    }
}
