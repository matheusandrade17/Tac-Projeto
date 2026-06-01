package com.gestao.academico.integration;

import com.gestao.academico.producer.AlunoProducer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlunoControllerTest{

    @LocalServerPort
    private int port;

    @MockBean
    private AlunoProducer alunoProducer;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/alunos"; // Ajuste para o caminho do seu Controller
    }

    @Test
    void deveRetornarStatus201_QuandoCadastrarAlunoValido() {
        String jsonAluno = "{ \"nome\": \"Bruno Sena\", \"email\": \"bruno@email.com\" }";

        given()
                .contentType(ContentType.JSON)
                .body(jsonAluno)
                .when()
                .post()
                .then()
                .statusCode(201); // Verifica se criou com sucesso
    }
}
