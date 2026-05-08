package com.gestao.academico.domain.entities;

import com.gestao.academico.event.AlunoCriadoEvent;
import com.gestao.academico.producer.AlunoProducer;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoProducer alunoProducer;

    public AlunoService(AlunoRepository alunoRepository, AlunoProducer alunoProducer) {
        this.alunoRepository = alunoRepository;
        this.alunoProducer = alunoProducer;
    }

    public Aluno salvar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio ou nulo");
        }
        Aluno saved = alunoRepository.save(aluno);
        AlunoCriadoEvent event = new AlunoCriadoEvent(saved.getId(), saved.getNome(), saved.getEmail(), LocalDateTime.now());
        alunoProducer.publicarAlunoCriado(event);
        return saved;
    }


    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    @SuppressWarnings("null")
    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    @SuppressWarnings("null")
    public void remover(Long id) {
        alunoRepository.deleteById(id);
    }
}