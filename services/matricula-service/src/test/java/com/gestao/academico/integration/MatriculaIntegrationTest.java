package com.gestao.academico.integration;

import com.gestao.academico.MatriculaServiceApplication;
import com.gestao.academico.domain.entities.Matricula;
import com.gestao.academico.domain.entities.MatriculaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration",
        "spring.rabbitmq.listener.simple.auto-startup=false",
        "spring.rabbitmq.listener.direct.auto-startup=false"
})
class MatriculaIntegrationTest {


    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private RabbitAdmin rabbitAdmin;

    @MockBean
    private ConnectionFactory connectionFactory;

    @Autowired
    private MatriculaRepository repository;


    @Test
    void devePersistirMatriculaNoPostgresContainer() {
        // Arrange
        Matricula matricula = new Matricula();
        matricula.setAlunoId(1L);
        matricula.setDisciplinaId(2L);
        matricula.setDataMatricula(LocalDate.now());
        matricula.setStatus("ATIVA");

        // Act
        Matricula saved = repository.save(matricula);
        Matricula found = repository.findById(saved.getId()).orElseThrow();

        // Assert
        assertThat(found.getId()).isNotNull();
        assertThat(found.getAlunoId()).isEqualTo(1L);
        assertThat(found.getDisciplinaId()).isEqualTo(2L);
    }
}

