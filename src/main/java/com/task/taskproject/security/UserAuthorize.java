package com.task.taskproject.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @Author : 최대준
 * @interfaceName : AdminAuthorize
 * @Description : @UserAuthorize 커스텀 어노테이션 > 일반 회원 API 검증용
 * @Since : 2024. 06. 24.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('USER')")
public @interface UserAuthorize {
}
