package com.fernandez.foro_hub.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-key")
                )
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Forum")
                        .description("API REST para foro de discusión con autenticación JWT, gestión de usuarios, preguntas y respuestas. Incluye frontend desplegado para pruebas.")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@forum.local"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://forum/licencia")));
    }
}
