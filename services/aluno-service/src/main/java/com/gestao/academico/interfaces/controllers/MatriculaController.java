package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Matricula;
import com.gestao.academico.infrastructure.repository.InMemoryMatriculaRepository;
import com.gestao.academico.interfaces.dto.MatriculaDTO;
import com.gestao.academico.messaging.InMemoryEventPublisher;
import com.gestao.academico.messaging.MatriculaRealizada;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

    private final InMemoryMatriculaRepository repo;
    private final InMemoryEventPublisher publisher;

    public MatriculaController(InMemoryMatriculaRepository repo, InMemoryEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @GetMapping
    public ResponseEntity<List<MatriculaDTO>> list() {
        return ResponseEntity.ok(repo.findAll().stream().map(this::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDTO> get(@PathVariable String id) {
        return repo.findById(id)
                .map(matricula -> ResponseEntity.ok(toDto(matricula)))
                .orElseThrow(() -> new ResourceNotFoundException("Matricula nao encontrada", "/api/v1/matriculas/" + id));
    }

    @PostMapping
    public ResponseEntity<MatriculaDTO> create(@Valid @RequestBody MatriculaDTO dto) {
        Matricula saved = repo.save(fromDto(dto));
        publisher.publish(new MatriculaRealizada(
                new MatriculaRealizada.Payload(
                        saved.getId(),
                        saved.getAlunoId(),
                        saved.getDisciplinaId(),
                        saved.getDataMatricula().toString()
                )));
        return ResponseEntity.created(URI.create("/api/v1/matriculas/" + saved.getId())).body(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaDTO> update(@PathVariable String id, @Valid @RequestBody MatriculaDTO dto) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Matricula nao encontrada", "/api/v1/matriculas/" + id);
        }

        Matricula matricula = fromDto(dto);
        matricula.setId(id);
        return ResponseEntity.ok(toDto(repo.save(matricula)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Matricula nao encontrada", "/api/v1/matriculas/" + id);
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private MatriculaDTO toDto(Matricula matricula) {
        MatriculaDTO dto = new MatriculaDTO();
        dto.setId(matricula.getId());
        dto.setAlunoId(matricula.getAlunoId());
        dto.setDisciplinaId(matricula.getDisciplinaId());
        dto.setDataMatricula(matricula.getDataMatricula());
        dto.setStatus(matricula.getStatus());
        return dto;
    }

    private Matricula fromDto(MatriculaDTO dto) {
        Matricula matricula = new Matricula();
        matricula.setId(dto.getId());
        matricula.setAlunoId(dto.getAlunoId());
        matricula.setDisciplinaId(dto.getDisciplinaId());
        matricula.setDataMatricula(dto.getDataMatricula());
        matricula.setStatus(dto.getStatus());
        return matricula;
    }
}
