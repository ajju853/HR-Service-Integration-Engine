package com.enterprise.attendance;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI attendanceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Attendance Service API")
                .description("Attendance tracking microservice — registers daily attendance and shift assignments for employees.")
                .version("1.0.0"));
    }
}
