package com.deckora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;



@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("Api Ecommerce Deckora")
            .version("1.1")
            .description("Con esta API se puede administrar el funcionamiento del ecommerce Deckora, incluyendo la creación, actualización y eliminación de usuarios, así como la gestión de productos.")
        );
    }
}
