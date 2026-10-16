package com.app.cinemabackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cinépolis API - Sistema Web de Gestión de Reservas")
                        .version("1.0.0")
                        .description("API REST para la gestión de cartelera, selección y concurrencia de butacas con timeout de 5 min, " +
                                     "catálogo de dulcería, confirmación de pagos y generación de boletos con código QR único.")
                        .contact(new Contact()
                                .name("Equipo de Backend - Desarrollo Web Integrado (UTP)")
                                .email("soporte@cinepolis-web.com"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .servers(List.of(
                        new Server().url("https://cinema-backend-mzeg.onrender.com").description("Servidor de Producción / Render"),
                        new Server().url("http://localhost:8080").description("Servidor de Desarrollo Local")
                ));
    }
}
