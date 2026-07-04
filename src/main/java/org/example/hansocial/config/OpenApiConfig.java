package org.example.hansocial.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hansocialOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hansocial API")
                        .description("REST API documentation for the Hansocial social media platform. " +
                                "Provides endpoints for user management, posts, comments, likes, and authentication.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Hansocial Team")));
    }
}
