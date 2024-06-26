package com.task.taskproject.controller;

import com.task.taskproject.controller.MemberController.API;
import com.task.taskproject.dto.ApiResponse;
import com.task.taskproject.dto.member.request.MemberUpdateRequest;
import com.task.taskproject.security.UserAuthorize;
import com.task.taskproject.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 최대준
 * @ClassName : MemberController
 * @Description : 회원용 API 컨트롤러
 * @Since : 2024. 06. 24.
 */
@Tag(name = "회원용 API")
@UserAuthorize // 커스텀 어노테이션 일반 사용자 API 검증
@RestController
@RequestMapping(API.BASE)
public class MemberController {
    public static class API {
        public static final String BASE = "/member";
        public static final String BASE_PATTERN = BASE + "/**";
    }

    @Resource(name = "MemberService")
    private MemberService memberService;

    /**
     * @Program Name : getMemberInfo
     * @Describe : 회원 정보 조회 API
     * @Param : User(user) 로그인한 사용자 정보
     * @Date : 2024. 06. 24.
     * @Writer : 최대준
     * @History : 2024. 06. 24. | 최대준 | 최초작성
     */
    @Operation(summary = "회원 정보 조회")
    @GetMapping
    public ApiResponse getMemberInfo(
        @AuthenticationPrincipal User user
    ) {
        return ApiResponse.success(memberService.getMemberInfo(user.getUsername()));
    }

    /**
     * @Program Name : deleteMember
     * @Describe : 회원 탈퇴 API
     * @Param : User(user) 로그인한 사용자 정보
     * @Date : 2024. 06. 24.
     * @Writer : 최대준
     * @History : 2024. 06. 24. | 최대준 | 최초작성
     * @Need : 탈퇴 후 로그아웃 처리, 탈퇴 회원과 연관된 전체 DB 삭제 기능 필요
     */
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ApiResponse deleteMember(
        @AuthenticationPrincipal User user
    ) {
        return ApiResponse.success(memberService.deleteMember(user.getUsername()));
    }

    /**
     * @Program Name : updateMember
     * @Describe : 회원 정보 수정 API
     * @Param1 : User(user) 로그인한 사용자 정보
     * @Param2 : MemberUpdateRequest(request) 변경할 사용자 정보
     * @Date : 2024. 06. 24.
     * @Writer : 최대준
     * @History : 2024. 06. 24. | 최대준 | 최초작성
     * @Need : 주민등록번호 변경 시 암호화 기능 추가 필요
     */
    @Operation(summary = "회원 정보 수정")
    @PutMapping
    public ApiResponse updateMember(
        @AuthenticationPrincipal User user,
        @RequestBody MemberUpdateRequest request
    ) {
        return ApiResponse.success(memberService.updateMember(user.getUsername(), request));
    }
}
