package com.odegaa.practiceproject.configurations.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(List.of(new Server().url("http://localhost:8083")))
                .info(new Info()
                        .title("Enterprise Resource Planning (ERP) System for Small Business Management")
                        .version("1.0.0-SNAPSHOT")
                        .description("Project Overview: This API provides a comprehensive digital accounting and management solution for a growing enterprise. " +
                                "The system is designed with a modular architecture to handle Human Resources, Customer Relations, and Sales Department operations. " +
                                "Detailed Module Descriptions: 1. HR Management handles full employee lifecycle, recruitment, and salary management. " +
                                "2. CRM Module manages customer onboarding, interaction history, and archiving. " +
                                "3. Sales and Marketing tracks advertising expenditures with pagination support. " +
                                "4. Executive Analytics provides advanced statistics for decision-making. " +
                                "Key Features: The system includes a robust role-based access control mechanism, JWT security, " +
                                "and an automated audit logging system for tracking all transactions."))
                .addSecurityItem(new SecurityRequirement().addList("JWT Authorization"))
                .components(new Components()
                        .addSecuritySchemes("JWT Authorization", new SecurityScheme()
                                .name("JWT Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}
