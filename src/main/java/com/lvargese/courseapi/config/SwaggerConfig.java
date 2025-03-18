package com.lvargese.courseapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Course Management System")
                        .description("API for managing Courses in schools")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Liyons Varghese")
                                .email("liyonsvarghese@gmail.com")
                                .url("https://liyonsvarghese.netlify.app/")
                        )
                );
    }
}