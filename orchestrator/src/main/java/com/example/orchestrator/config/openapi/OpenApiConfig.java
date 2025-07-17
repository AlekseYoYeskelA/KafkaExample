//package com.example.orchestrator.config.openapi;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Конфигурация OpenAPI (Swagger) для документации REST API.
// */
//@Configuration
//public class OpenApiConfig {
//    /**
//     * Настраивает кастомную документацию OpenAPI.
//     *
//     * @return OpenAPI конфигурация
//     */
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("User Orchestration API")
//                        .version("1.0")
//                        .description("API для управления процессом создания пользователей через сагу"));
//    }
//}
