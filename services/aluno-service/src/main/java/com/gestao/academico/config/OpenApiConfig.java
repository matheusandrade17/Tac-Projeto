package com.gestao.academico.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Aluno Service API")
                        .version("v1")
                        .description("API de Gestão de Alunos - Responsável pelo cadastro, consulta, atualização e remoção de alunos.")
                        .contact(new Contact()
                                .name("Equipe Gestão Acadêmica")
                                .email("suporte@gestaoacademico.com")));
    }
}

