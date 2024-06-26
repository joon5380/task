package com.task.taskproject;

import com.task.taskproject.common.ApiStatus;
import com.task.taskproject.controller.AdminController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtResponseTest {
    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzNDpBRE1JTiIsImlzcyI6InVzZXIxMjM0IiwiaWF0IjoxNjg1MDI0MjYzLCJleHAiOjE2ODUwMjQyNjZ9.J7iOhSgIi6VzMmjK-6DspCLhgtRB2nTP65WhstJGUx5yEDT0dj-CIvP1gRIZGgS8PNiGbvVkx5nc9z6V_FFFjA"; // 만료된 토큰
    private static final String TAMPERED_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzNDpBRE1JTiIsImlzcyI6InVzZXIxMjM0IiwiaWF0IjoxNjg1MDI0MjYzLCJleHAiOjE2ODUwMjQyNjZ9.NhzMM2YpR7ZvOSVXRBH3XEGjmtEGPaZixfRwcYWiF0QygrDwfusbS8x8RIG5h-IFkHqZECUO4T7Eu9ktv15yl"; // 조작된 토큰
    private static final String MALFORMED_TOKEN = "1234567890"; // 잘못된 토큰

    @Autowired
    private MockMvc mvc;

    private MockHttpServletRequestBuilder requestBuilder() {
        return get(AdminController.API.BASE + AdminController.API.GET_USER_LIST).contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void notExistTokenTest() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(requestBuilder());
        // then
        actions.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(ApiStatus.ERROR.name()))
                .andExpect(jsonPath("$.message").value("접근이 거부되었습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void expiredTokenTest() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(requestBuilder().header(HttpHeaders.AUTHORIZATION, "Bearer " + EXPIRED_TOKEN));
        // then
        actions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(ApiStatus.ERROR.name()))
                .andExpect(jsonPath("$.message").value("토큰이 만료되었습니다. 다시 로그인해주세요."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void tamperedTokenTest() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(requestBuilder().header(HttpHeaders.AUTHORIZATION, "Bearer " + TAMPERED_TOKEN));
        // then
        actions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(ApiStatus.ERROR.name()))
                .andExpect(jsonPath("$.message").value("토큰이 유효하지 않습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void malformedTokenTest() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(requestBuilder().header(HttpHeaders.AUTHORIZATION, "Bearer " + MALFORMED_TOKEN));
        // then
        actions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(ApiStatus.ERROR.name()))
                .andExpect(jsonPath("$.message").value("올바르지 않은 토큰입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
