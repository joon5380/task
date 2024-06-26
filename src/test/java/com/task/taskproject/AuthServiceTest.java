package com.task.taskproject;

import com.task.taskproject.common.MemberType;
import com.task.taskproject.dto.signIn.request.SignInRequest;
import com.task.taskproject.dto.signIn.response.SignInResponse;
import com.task.taskproject.dto.signUp.request.SignUpRequest;
import com.task.taskproject.dto.signUp.response.SignUpResponse;
import com.task.taskproject.entity.Member;
import com.task.taskproject.repository.MemberRepository;
import com.task.taskproject.service.AuthService;
import jakarta.annotation.Resource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthServiceTest {
    @Resource(name = "AuthService")
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    void registerSuccessTest() {
        // given
        SignUpRequest request = new SignUpRequest("user1234", "1234", "동탁", "921108-1582816");
        // when
        SignUpResponse response = authService.register(request);
        // then
        assertThat(response.userId()).isEqualTo("user1234");
        assertThat(response.name()).isEqualTo("동탁");
        assertThat(response.regNo()).isEqualTo("rni96fT3bBmumSyOCxfg2g==");
    }

    @Test
    void registerIdExistTest() {
        // given
        memberRepository.save(Member.builder()
                .userId("user1234")
                .password(encoder.encode("1234"))
                .name("동탁")
                .regNo("rni96fT3bBmumSyOCxfg2g==")
                .type(MemberType.USER)
                .build()
        );
        SignUpRequest request = new SignUpRequest("user1234", "1234", "동탁", "921108-1582816");
        // when
        // then
        Assertions.assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 아이디입니다.");
    }

    @Test
    void loginSuccessTest() {
        // given
        memberRepository.save(Member.builder()
                .userId("user1234")
                .password(encoder.encode("1234"))
                .name("최대준")
                .regNo("990424-1000000")
                .type(MemberType.USER)
                .build()
        );
        // when
        SignInResponse response = authService.login(new SignInRequest("user1234", "1234"));
        // then
        assertThat(response.name()).isEqualTo("최대준");
        assertThat(response.type()).isEqualTo(MemberType.USER);
    }

    @Test
    void loginFailedTest() {
        // given
        memberRepository.save(Member.builder()
                .userId("user1234")
                .password("1234")
                .name("최대준")
                .regNo("990424-1000000")
                .build()
        );
        // when
        // then
        Assertions.assertThatThrownBy(() -> authService.login(new SignInRequest("user1234", "1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
}
