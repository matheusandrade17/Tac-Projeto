package com.gestao.academico.domain.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa um Aluno no sistema acadêmico")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do aluno", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome completo do aluno", example = "Bruno Sena", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @Schema(description = "Endereço de e-mail do aluno", example = "bruno@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
