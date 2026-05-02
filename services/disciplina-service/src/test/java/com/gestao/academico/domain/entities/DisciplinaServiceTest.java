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
class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    private DisciplinaService disciplinaService;

    @BeforeEach
    void setUp() {
        disciplinaService = new DisciplinaService(disciplinaRepository);
    }

    @Test
    void deveSalvarDisciplinaComSucesso() {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome("Cálculo I");
        disciplina.setCodigo("CALC-001");
        disciplina.setDescricao("Introdução ao cálculo");
        disciplina.setCargaHoraria(60);

        when(disciplinaRepository.save(any(Disciplina.class))).thenReturn(disciplina);

        Disciplina resultado = disciplinaService.salvar(disciplina);

        assertNotNull(resultado);
        assertEquals("Cálculo I", resultado.getNome());
        verify(disciplinaRepository, times(1)).save(disciplina);
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNulo() {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(null);
        disciplina.setCodigo("CALC-001");

        assertThrows(IllegalArgumentException.class, () -> {
            disciplinaService.salvar(disciplina);
        });
    }

    @Test
    void deveLancarExcecaoQuandoCodigoForNulo() {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome("Cálculo I");
        disciplina.setCodigo(null);

        assertThrows(IllegalArgumentException.class, () -> {
            disciplinaService.salvar(disciplina);
        });
    }
}
