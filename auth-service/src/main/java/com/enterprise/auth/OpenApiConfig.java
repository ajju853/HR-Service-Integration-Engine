package com.enterprise.auth;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI authOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Auth Service API")
                .description("Authentication microservice — generates JWT Bearer tokens for HR system access. Single endpoint: POST /auth/login.")
                .version("1.0.0"));
    }
}
