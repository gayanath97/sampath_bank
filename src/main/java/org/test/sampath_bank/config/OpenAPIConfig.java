package org.test.sampath_bank.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI sampathBankOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Sampath Bank Employee API")
                .description("API documentation for Sampath Bank Employee Management System")
                .version("1.0")
                .contact(new Contact()
                    .name("Your Name")
                    .email("your.email@example.com")));
    }
} 