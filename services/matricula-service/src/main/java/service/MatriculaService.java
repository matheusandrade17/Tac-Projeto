package service;

import dto.MatriculaDto;
import model.Matricula;
import repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatriculaService {

    private final MatriculaRepository repo;

    public MatriculaService(MatriculaRepository repo) {
        this.repo = repo;
    }

    public Matricula criarMatricula(MatriculaDto dto) {
        Matricula matricula = new Matricula();
        matricula.setClienteId(dto.getAlunoId());
        matricula.setCriadoEm(LocalDateTime.now());
        // outros campos

        Matricula saved = repo.save(matricula);

        return saved;
    }
}
