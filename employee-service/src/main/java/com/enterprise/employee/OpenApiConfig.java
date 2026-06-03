package com.enterprise.employee;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI employeeOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Employee Service API")
                .description("Employee CRUD microservice — part of the HR Service Integration Engine. " +
                    "Supports create, read, update, delete operations with auto-generated employee codes and duplicate email detection.")
                .version("1.0.0"));
    }
}
