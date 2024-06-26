package com.task.taskproject.service;

import com.task.taskproject.dto.signIn.request.SignInRequest;
import com.task.taskproject.dto.signIn.response.SignInResponse;
import com.task.taskproject.dto.signUp.request.SignUpRequest;
import com.task.taskproject.dto.signUp.response.SignUpResponse;

/**
 * @Author : 최대준
 * @InterfaceName : AuthService
 * @Description : 회원가입 및 로그인 API 구현체 인터페이스
 * @Since : 2024. 06. 24.
 */
public interface AuthService {
    SignUpResponse register(SignUpRequest request);
    SignInResponse login(SignInRequest request);
}
