package com.gestao.academico.domain.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @Test
    void deveSalvarAlunoComSucesso() {
        // Arrange
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setEmail("joao@example.com");
        when(alunoRepository.save(aluno)).thenReturn(aluno);

        // Act
        Aluno resultado = alunoService.salvar(aluno);

        // Assert
        assertThat(resultado).isEqualTo(aluno);
    }

    @Test
    void deveLancarExcecaoQuandoNomeForVazio() {
        // Arrange
        Aluno aluno = new Aluno();
        aluno.setNome("");
        aluno.setEmail("joao@example.com");

        // Act & Assert
        assertThatThrownBy(() -> alunoService.salvar(aluno))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Nome não pode ser vazio ou nulo");
    }
}