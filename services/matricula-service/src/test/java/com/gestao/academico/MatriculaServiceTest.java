package com.gestao.academico;

import com.gestao.academico.domain.entities.Matricula;
import com.gestao.academico.domain.entities.MatriculaService;
import com.gestao.academico.event.MatriculaCriadaEvent;
import com.gestao.academico.mensageria.listeners.ValidacaoListener;
import com.gestao.academico.producer.MatriculaProducer;
import com.gestao.academico.domain.entities.MatriculaRepository;
import dto.MatriculaDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    MatriculaRepository matriculaRepository;

    @Mock
    ValidacaoListener validacaoListener;

    @Mock
    MatriculaProducer matriculaProducer;

    @InjectMocks
    MatriculaService service;

    @Test
    @DisplayName("Deve salvar matrícula com sucesso")
    void matriculaRealizadaComSucesso() {
        // Arrange
        MatriculaDto dto = new MatriculaDto();
        dto.setAlunoId("1");
        dto.setDisciplinaId("2");

        Matricula matricula = new Matricula();
        matricula.setAlunoId(1L);
        matricula.setDisciplinaId(2L);
        matricula.setDataMatricula(LocalDateTime.now().toLocalDate());
        matricula.setStatus("ATIVA");

        Matricula saved = new Matricula();
        saved.setId(10L);
        saved.setAlunoId(1L);
        saved.setDisciplinaId(2L);
        saved.setDataMatricula(matricula.getDataMatricula());
        saved.setStatus("ATIVA");

        when(validacaoListener.isAlunoValido(1L)).thenReturn(true);
        when(validacaoListener.isDisciplinaValida(2L)).thenReturn(true);
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(saved);

        // Act
        com.gestao.academico.domain.entities.Matricula result = service.salvar(matricula);


        // Assert
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getAlunoId()).isEqualTo(1L);
        assertThat(result.getDisciplinaId()).isEqualTo(2L);
        verify(matriculaRepository, times(1)).save(any(Matricula.class));
        verify(matriculaProducer, times(1)).publicarMatriculaCriada(any(MatriculaCriadaEvent.class));
    }

    @Test
    @DisplayName("Deve falhar quando aluno não existe")
    void erroAlunoInexistente() {
        // Arrange
        MatriculaDto dto = new MatriculaDto();
        dto.setAlunoId("999");
        dto.setDisciplinaId("2");

        when(validacaoListener.isAlunoValido(999L)).thenReturn(false);

        // Act / Assert
        com.gestao.academico.domain.entities.Matricula m = new com.gestao.academico.domain.entities.Matricula();
        m.setAlunoId(999L);
        m.setDisciplinaId(2L);

        assertThatThrownBy(() -> service.salvar(m))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aluno");


        verify(matriculaRepository, never()).save(any());
        verifyNoInteractions(matriculaProducer);
    }

    @Test
    @DisplayName("Deve falhar quando disciplina não existe")
    void erroDisciplinaInexistente() {
        // Arrange
        MatriculaDto dto = new MatriculaDto();
        dto.setAlunoId("1");
        dto.setDisciplinaId("999");

        when(validacaoListener.isAlunoValido(1L)).thenReturn(true);
        when(validacaoListener.isDisciplinaValida(999L)).thenReturn(false);

        // Act / Assert
        com.gestao.academico.domain.entities.Matricula m = new com.gestao.academico.domain.entities.Matricula();
        m.setAlunoId(1L);
        m.setDisciplinaId(999L);

        assertThatThrownBy(() -> service.salvar(m))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Disciplina");


        verify(matriculaRepository, never()).save(any());
        verifyNoInteractions(matriculaProducer);
    }

    @Test
    @DisplayName("Deve falhar quando dados inválidos forem fornecidos")
    void erroDadosInvalidos() {
        // Arrange
        MatriculaDto dto = new MatriculaDto();
        dto.setAlunoId("");
        dto.setDisciplinaId(null);

        // Act / Assert
        com.gestao.academico.domain.entities.Matricula m = new com.gestao.academico.domain.entities.Matricula();
        m.setAlunoId(null);
        m.setDisciplinaId(null);

        assertThatThrownBy(() -> service.salvar(m))
                .isInstanceOf(IllegalArgumentException.class);


        verifyNoInteractions(matriculaRepository);
        verifyNoInteractions(matriculaProducer);
    }

    @Test
    @DisplayName("Deve salvar através do método salvar() validando campos")
    void salvarComSucesso() {
        // Arrange
        Matricula matricula = new Matricula();
        matricula.setAlunoId(1L);
        matricula.setDisciplinaId(2L);
        matricula.setStatus("ATIVA");
        matricula.setDataMatricula(LocalDateTime.now().toLocalDate());

        Matricula saved = new Matricula();
        saved.setId(55L);
        saved.setAlunoId(1L);
        saved.setDisciplinaId(2L);
        saved.setStatus("ATIVA");
        saved.setDataMatricula(matricula.getDataMatricula());

        when(validacaoListener.isAlunoValido(1L)).thenReturn(true);
        when(validacaoListener.isDisciplinaValida(2L)).thenReturn(true);
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(saved);

        // Act
        Matricula result = service.salvar(matricula);

        // Assert
        assertThat(result.getId()).isEqualTo(55L);
        ArgumentCaptor<MatriculaCriadaEvent> captor = ArgumentCaptor.forClass(MatriculaCriadaEvent.class);
        verify(matriculaProducer).publicarMatriculaCriada(captor.capture());
        assertThat(captor.getValue().getAlunoId()).isEqualTo(1L);
    }
}

