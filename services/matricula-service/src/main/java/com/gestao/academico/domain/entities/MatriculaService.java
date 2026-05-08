package com.gestao.academico.domain.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final RestTemplate restTemplate;

    @Value("${services.aluno.base-url}")
    private String alunoBaseUrl;

    @Value("${services.disciplina.base-url}")
    private String disciplinaBaseUrl;



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


        String urlAluno = alunoBaseUrl + "/" + matricula.getAlunoId();
        String urlDisciplina = disciplinaBaseUrl + "/" + matricula.getDisciplinaId();

        try {
            restTemplate.getForEntity(urlAluno, Object.class);
            restTemplate.getForEntity(urlDisciplina, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Matrícula negada: Aluno/Disciplina não encontrado no sistema acadêmico.");
        }

        return matriculaRepository.save(matricula);
    }
}