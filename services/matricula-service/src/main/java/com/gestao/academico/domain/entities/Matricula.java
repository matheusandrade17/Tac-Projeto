package com.gestao.academico.domain.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa uma Matrícula no sistema acadêmico")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da matrícula", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID do aluno matriculado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long alunoId;

    @Schema(description = "ID da disciplina na qual o aluno está matriculado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long disciplinaId;

    @Schema(description = "Data da matrícula", example = "2024-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dataMatricula;

    @Schema(description = "Status da matrícula (ATIVA, CANCELADA, CONCLUIDA)", example = "ATIVA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String status;
}
