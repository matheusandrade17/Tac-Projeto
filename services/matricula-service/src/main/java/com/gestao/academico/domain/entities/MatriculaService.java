package com.gestao.academico.domain.entities;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final RestTemplate restTemplate;


    public MatriculaService(MatriculaRepository matriculaRepository, RestTemplate restTemplate) {
        this.matriculaRepository = matriculaRepository;
        this.restTemplate = restTemplate;
    }

    public Matricula salvar(Matricula matricula) {
        if (matricula.getAlunoId() == null) {
            throw new IllegalArgumentException("Aluno ID não pode ser nulo");
        }
        if (matricula.getDisciplinaId() == null) {
            throw new IllegalArgumentException("Disciplina ID não pode ser nulo");
        }


        String urlAluno = "http://localhost:8081/api/v1/alunos/" + matricula.getAlunoId();

        try {

            restTemplate.getForEntity(urlAluno, Object.class);
        } catch (Exception e) {

            throw new RuntimeException("Matrícula negada: Aluno não encontrado no sistema acadêmico.");
        }


        return matriculaRepository.save(matricula);
    }
}