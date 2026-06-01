package com.gestao.academico.integration;

import com.gestao.academico.producer.AlunoProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoProducer alunoProducer;

    @Test
    void deveRetornarStatus201_QuandoCadastrarAlunoValido() throws Exception {
        String jsonAluno = "{\"nome\": \"Bruno Sena\", \"email\": \"bruno@email.com\"}";

        mockMvc.perform(post("/api/v1/alunos")
                .contentType("application/json")
                .content(jsonAluno))
                .andExpect(status().isCreated());
    }
}
