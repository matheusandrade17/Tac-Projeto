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
@Schema(description = "Entidade que representa uma Avaliação no sistema acadêmico")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da avaliação", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID da disciplina da avaliação", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long disciplinaId;

    @Schema(description = "ID do aluno que realizou a avaliação", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long alunoId;

    @Schema(description = "Tipo de avaliação (PROVA, TRABALHO, PROJETO)", example = "PROVA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tipo;

    @Schema(description = "Nota obtida na avaliação", example = "85.5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double nota;

    @Schema(description = "Data da avaliação", example = "2024-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dataAvaliacao;
}
