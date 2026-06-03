package com.enterprise.notification;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI notificationOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Notification Service API")
                .description("Simulated email/SMS notification microservice — accepts send-email and send-sms requests and returns success responses.")
                .version("1.0.0"));
    }
}
