package com.gestao.academico.domain.entities;

import java.time.LocalDate;

public class Matricula {
    private String id;
    private String alunoId;
    private String disciplinaId;
    private LocalDate dataMatricula;
    private String status;

    public Matricula() {
    }

    public Matricula(String id, String alunoId, String disciplinaId, LocalDate dataMatricula, String status) {
        this.id = id;
        this.alunoId = alunoId;
        this.disciplinaId = disciplinaId;
        this.dataMatricula = dataMatricula;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }

    public String getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(String disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
