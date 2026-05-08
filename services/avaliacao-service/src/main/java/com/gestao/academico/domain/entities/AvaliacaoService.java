package com.gestao.academico.domain.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final RestTemplate restTemplate;

    @Value("${services.aluno.base-url}")
    private String alunoBaseUrl;

    @Value("${services.disciplina.base-url}")
    private String disciplinaBaseUrl;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, RestTemplate restTemplate) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.restTemplate = restTemplate;
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        if (avaliacao.getAlunoId() == null) {
            throw new IllegalArgumentException("Aluno ID não pode ser nulo");
        }
        if (avaliacao.getDisciplinaId() == null) {
            throw new IllegalArgumentException("Disciplina ID não pode ser nulo");
        }

        String urlAluno = alunoBaseUrl + "/" + avaliacao.getAlunoId();
        String urlDisciplina = disciplinaBaseUrl + "/" + avaliacao.getDisciplinaId();

        try {
            restTemplate.getForEntity(urlAluno, Object.class);
            restTemplate.getForEntity(urlDisciplina, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Avaliação negada: Aluno/Disciplina não encontrado no sistema acadêmico.");
        }

        return avaliacaoRepository.save(avaliacao);
    }
}

