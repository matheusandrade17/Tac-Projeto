package com.gestao.academico.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI matriculaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Matrículas")
                        .description("API para gerenciamento de matrículas acadêmicas")
                        .version("v1")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("dev@gestao.academico"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
