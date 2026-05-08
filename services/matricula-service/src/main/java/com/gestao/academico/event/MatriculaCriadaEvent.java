package com.gestao.academico.event;

import java.time.LocalDateTime;

public class MatriculaCriadaEvent {

    private Long id;
    private Long alunoId;
    private String codigoDisciplina;
    private LocalDateTime dataMatricula;

    public MatriculaCriadaEvent() {}

    public MatriculaCriadaEvent(Long id, Long alunoId, String codigoDisciplina, LocalDateTime dataMatricula) {
        this.id = id;
        this.alunoId = alunoId;
        this.codigoDisciplina = codigoDisciplina;
        this.dataMatricula = dataMatricula;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public LocalDateTime getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDateTime dataMatricula) {
        this.dataMatricula = dataMatricula;
    }
}