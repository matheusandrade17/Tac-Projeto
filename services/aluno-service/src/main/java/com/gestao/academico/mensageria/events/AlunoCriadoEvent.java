package com.gestao.academico.mensageria.events;

import java.time.Instant;

public record AlunoCriadoEvent(Long alunoId, String nome, Instant timestamp) {}

