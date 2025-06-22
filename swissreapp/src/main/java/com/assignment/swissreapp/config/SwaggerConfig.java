package com.assignment.swissreapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Excel Operations API")
                        .version("1.0")
                        .description("API documentation for performing Excel-based employee operations like gratuity check, hierarchy building, and salary analysis.")
                        .contact(new Contact()
                                .name("SwissRe Dev Team")
                                .email("support@swissre.com")
                                .url("https://www.swissre.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("employee")
                .pathsToMatch("/api/excel/**")
                .build();
    }
}
