package com.daom.api_usuarios.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigSwagger {


    /**
     * http://localhost:5095/swagger-ui/index.html
     * @return
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot API REST")
                        .description("GESTION DE USUARIOS Api rest")
                        .version("1.0")
                        .contact(new Contact()
                                .name("DAOM")
                                .url("http://www.google.com")
                                .email("daom@example.com")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Más información")
                        .url("http://www.google.com")
                );
    }
}
