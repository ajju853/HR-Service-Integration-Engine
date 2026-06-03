package com.enterprise.payroll;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI payrollOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Payroll Service API")
                .description("Payroll management microservice — creates and queries payroll records linked to employees.")
                .version("1.0.0"));
    }
}
