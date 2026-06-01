package com.gestao.academico.domain.entities;

import com.gestao.academico.integration.AlunoClient;
import com.gestao.academico.integration.DisciplinaClient;
import com.gestao.academico.producer.MatriculaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @Mock
    private AlunoClient alunoClient;

    @Mock
    private DisciplinaClient disciplinaClient;

    @Mock
    private MatriculaProducer matriculaProducer;

    private MatriculaService matriculaService;

    @BeforeEach
    void setUp() {
        matriculaService = new MatriculaService(matriculaRepository, alunoClient, disciplinaClient, matriculaProducer);
    }

    @Test
    @SuppressWarnings("null")
    void deveSalvarMatriculaComSucesso() {
        Matricula matricula = new Matricula();
        matricula.setAlunoId(1L);
        matricula.setDisciplinaId(1L);
        matricula.setStatus("ATIVA");

        when(alunoClient.alunoExiste(1L)).thenReturn(CompletableFuture.completedFuture(true));
        when(disciplinaClient.disciplinaExiste(1L)).thenReturn(CompletableFuture.completedFuture(true));
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        Matricula resultado = matriculaService.salvar(matricula);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getAlunoId());
        verify(matriculaRepository, times(1)).save(matricula);
    }

    @Test
    void deveLancarExcecaoQuandoAlunoNaoExistir() {
        Matricula matricula = new Matricula();
        matricula.setAlunoId(99L);
        matricula.setDisciplinaId(1L);

        when(alunoClient.alunoExiste(99L)).thenReturn(CompletableFuture.completedFuture(false));

        assertThrows(RuntimeException.class, () -> matriculaService.salvar(matricula));
        verify(matriculaRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoAlunoIdForNulo() {
        Matricula matricula = new Matricula();
        matricula.setAlunoId(null);
        matricula.setDisciplinaId(1L);

        assertThrows(IllegalArgumentException.class, () -> matriculaService.salvar(matricula));
    }

    @Test
    void deveLancarExcecaoQuandoDisciplinaIdForNulo() {
        Matricula matricula = new Matricula();
        matricula.setAlunoId(1L);
        matricula.setDisciplinaId(null);

        assertThrows(IllegalArgumentException.class, () -> matriculaService.salvar(matricula));
    }
}

