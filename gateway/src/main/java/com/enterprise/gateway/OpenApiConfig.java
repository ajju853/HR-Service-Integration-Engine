package com.enterprise.gateway;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI gatewayOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Gateway")
                .description("Spring Cloud Gateway acting as the single entry point for all microservices. " +
                    "Validates JWT Bearer tokens on every request and routes to the appropriate backend service.")
                .version("1.0.0"));
    }
}
