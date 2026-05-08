package com.gestao.academico.mensageria.events;

import java.time.Instant;

public record MatriculaCriadaEvent(Long matriculaId, Long alunoId, Long disciplinaId, Instant timestamp) {}

