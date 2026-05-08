package com.gestao.academico.mensageria.events;

import java.time.Instant;

public record AvaliacaoCriadaEvent(
        Long avaliacaoId,
        Long alunoId,
        Long disciplinaId,
        String tipo,
        Double nota,
        Instant timestamp
) {}

