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
@Schema(description = "Entidade que representa uma Disciplina no sistema acadêmico")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da disciplina", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome da disciplina", example = "Cálculo I", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @Schema(description = "Código da disciplina", example = "CALC-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigo;

    @Schema(description = "Descrição da disciplina", example = "Introdução ao cálculo diferencial e integral")
    private String descricao;

    @Schema(description = "Carga horária da disciplina em horas", example = "60", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cargaHoraria;
}
