package com.gestao.academico.domain.entities;

import com.gestao.academico.mensageria.listeners.ValidacaoListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private ValidacaoListener validacaoListener;

    private AvaliacaoService avaliacaoService;

    @BeforeEach
    void setUp() {
        avaliacaoService =
                new AvaliacaoService(avaliacaoRepository, validacaoListener);
    }

    @Test
    @SuppressWarnings("null")
    void deveSalvarAvaliacaoComSucesso() {

        when(validacaoListener.alunoExiste(anyLong()))
                .thenReturn(true);

        when(validacaoListener.disciplinaExiste(anyLong()))
                .thenReturn(true);

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAlunoId(1L);
        avaliacao.setDisciplinaId(1L);
        avaliacao.setTipo("PROVA");
        avaliacao.setNota(85.5);

        when(avaliacaoRepository.save(any(Avaliacao.class)))
                .thenReturn(avaliacao);

        Avaliacao resultado = avaliacaoService.salvar(avaliacao);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getAlunoId());

        verify(avaliacaoRepository, times(1))
                .save(avaliacao);
    }

    @Test
    void deveLancarExcecaoQuandoAlunoIdForNulo() {

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAlunoId(null);
        avaliacao.setDisciplinaId(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            avaliacaoService.salvar(avaliacao);
        });
    }

    @Test
    void deveLancarExcecaoQuandoDisciplinaIdForNulo() {

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAlunoId(1L);
        avaliacao.setDisciplinaId(null);

        assertThrows(IllegalArgumentException.class, () -> {
            avaliacaoService.salvar(avaliacao);
        });
    }
}