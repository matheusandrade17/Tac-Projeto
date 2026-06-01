package com.gestao.academico.domain.entities;

import java.util.Optional;

/**
 * Classe mantida por legado. O armazenamento em memória foi removido.
 * Este facade não deve mais ser usado no fluxo real.
 */
@Deprecated
public class MatriculaControllerFacade {

    public Optional<Matricula> buscarPorId(Long id) {
        throw new UnsupportedOperationException("MatriculaControllerFacade legado não suporta mais armazenamento em memória.");
    }
}



