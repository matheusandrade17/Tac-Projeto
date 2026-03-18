package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Disciplina;
import com.gestao.academico.infrastructure.repository.InMemoryDisciplinaRepository;
import com.gestao.academico.interfaces.dto.DisciplinaDTO;
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
@RequestMapping("/api/v1/disciplinas")
public class DisciplinaController {

    private final InMemoryDisciplinaRepository repo;

    public DisciplinaController(InMemoryDisciplinaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<DisciplinaDTO>> list() {
        return ResponseEntity.ok(repo.findAll().stream().map(this::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaDTO> get(@PathVariable String id) {
        return repo.findById(id)
                .map(disciplina -> ResponseEntity.ok(toDto(disciplina)))
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina nao encontrada", "/api/v1/disciplinas/" + id));
    }

    @PostMapping
    public ResponseEntity<DisciplinaDTO> create(@Valid @RequestBody DisciplinaDTO dto) {
        Disciplina saved = repo.save(fromDto(dto));
        return ResponseEntity.created(URI.create("/api/v1/disciplinas/" + saved.getId())).body(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaDTO> update(@PathVariable String id, @Valid @RequestBody DisciplinaDTO dto) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Disciplina nao encontrada", "/api/v1/disciplinas/" + id);
        }

        Disciplina disciplina = fromDto(dto);
        disciplina.setId(id);
        return ResponseEntity.ok(toDto(repo.save(disciplina)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Disciplina nao encontrada", "/api/v1/disciplinas/" + id);
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private DisciplinaDTO toDto(Disciplina disciplina) {
        DisciplinaDTO dto = new DisciplinaDTO();
        dto.setId(disciplina.getId());
        dto.setCodigo(disciplina.getCodigo());
        dto.setNome(disciplina.getNome());
        dto.setCreditos(disciplina.getCreditos());
        return dto;
    }

    private Disciplina fromDto(DisciplinaDTO dto) {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(dto.getId());
        disciplina.setCodigo(dto.getCodigo());
        disciplina.setNome(dto.getNome());
        disciplina.setCreditos(dto.getCreditos());
        return disciplina;
    }
}
