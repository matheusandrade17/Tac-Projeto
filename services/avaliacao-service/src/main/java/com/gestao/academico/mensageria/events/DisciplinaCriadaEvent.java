package com.gestao.academico.mensageria.events;

import java.time.Instant;

public record DisciplinaCriadaEvent(Long disciplinaId, String codigo, Instant timestamp) {}

