package com.gestao.academico.messaging;

import java.time.Instant;

public class MatriculaRealizada {
    private final String eventType = "MatriculaRealizada";
    private final Instant timestamp;
    private final Payload payload;

    public MatriculaRealizada(Payload payload) {
        this.timestamp = Instant.now();
        this.payload = payload;
    }

    public String getEventType() {
        return eventType;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Payload getPayload() {
        return payload;
    }

    public static class Payload {
        private final String matriculaId;
        private final String alunoId;
        private final String disciplinaId;
        private final String dataMatricula;

        public Payload(String matriculaId, String alunoId, String disciplinaId, String dataMatricula) {
            this.matriculaId = matriculaId;
            this.alunoId = alunoId;
            this.disciplinaId = disciplinaId;
            this.dataMatricula = dataMatricula;
        }

        public String getMatriculaId() {
            return matriculaId;
        }

        public String getAlunoId() {
            return alunoId;
        }

        public String getDisciplinaId() {
            return disciplinaId;
        }

        public String getDataMatricula() {
            return dataMatricula;
        }
    }
}
