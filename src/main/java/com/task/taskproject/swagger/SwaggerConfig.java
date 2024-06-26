package com.task.taskproject.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : 최대준
 * @ClassName : SwaggerConfig
 * @Description : Swagger 환경설정
 * @Since : 2024. 06. 25.
 */
@Configuration
public class SwaggerConfig {
    private static final String SECURITY_SCHEME_NAME = "authorization";

    @Bean
    public OpenAPI swaggerApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .info(new Info()
                        .title("삼쩜삼 백엔드 엔지니어 채용 과제")
                        .description("최대준 joon5380@gmail.com")
                        .version("1.0.0"));
    }
}
