package com.gestao.academico.domain.entities;

import com.gestao.academico.integration.AlunoClient;
import com.gestao.academico.integration.DisciplinaClient;
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
class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private AlunoClient alunoClient;

    @Mock
    private DisciplinaClient disciplinaClient;

    private AvaliacaoService avaliacaoService;

    @BeforeEach
    void setUp() {
        avaliacaoService =
                new AvaliacaoService(avaliacaoRepository, alunoClient, disciplinaClient);
    }

    @Test
    @SuppressWarnings("null")
    void deveSalvarAvaliacaoComSucesso() {

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAlunoId(1L);
        avaliacao.setDisciplinaId(1L);
        avaliacao.setTipo("PROVA");
        avaliacao.setNota(85.5);

        when(alunoClient.alunoExiste(1L))
                .thenReturn(CompletableFuture.completedFuture(true));

        when(disciplinaClient.disciplinaExiste(1L))
                .thenReturn(CompletableFuture.completedFuture(true));

        when(avaliacaoRepository.save(any(Avaliacao.class)))
                .thenReturn(avaliacao);

        Avaliacao resultado = avaliacaoService.salvar(avaliacao);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getAlunoId());

        verify(avaliacaoRepository, times(1))
                .save(avaliacao);
    }

    @Test
    void deveLancarExcecaoQuandoDisciplinaNaoExistir() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAlunoId(1L);
        avaliacao.setDisciplinaId(99L);

        when(alunoClient.alunoExiste(1L))
                .thenReturn(CompletableFuture.completedFuture(true));
        when(disciplinaClient.disciplinaExiste(99L))
                .thenReturn(CompletableFuture.completedFuture(false));

        assertThrows(RuntimeException.class, () -> avaliacaoService.salvar(avaliacao));
        verify(avaliacaoRepository, never()).save(any());
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
