package com.gestao.academico.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    private MatriculaService matriculaService;

    @BeforeEach
    void setUp() {
        matriculaService = new MatriculaService(matriculaRepository);
    }

    @Test
    void deveSalvarMatriculaComSucesso() {
        Matricula matricula = new Matricula();
        matricula.setAlunoId(1L);
        matricula.setDisciplinaId(1L);
        matricula.setStatus("ATIVA");

        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        Matricula resultado = matriculaService.salvar(matricula);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getAlunoId());
        verify(matriculaRepository, times(1)).save(matricula);
    }

    @Test
    void deveLancarExcecaoQuandoAlunoIdForNulo() {
        Matricula matricula = new Matricula();
        matricula.setAlunoId(null);
        matricula.setDisciplinaId(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            matriculaService.salvar(matricula);
        });
    }

    @Test
    void deveLancarExcecaoQuandoDisciplinaIdForNulo() {
        Matricula matricula = new Matricula();
        matricula.setAlunoId(1L);
        matricula.setDisciplinaId(null);

        assertThrows(IllegalArgumentException.class, () -> {
            matriculaService.salvar(matricula);
        });
    }
}
