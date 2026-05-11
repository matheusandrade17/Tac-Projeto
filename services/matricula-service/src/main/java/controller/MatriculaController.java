package controller;

import dto.MatriculaDto;
import model.Matricula;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestao.academico.domain.entities.MatriculaService;

@RestController
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public ResponseEntity<Matricula> criar(@RequestBody MatriculaDto dto) {
        Matricula saved = matriculaService.criarMatricula(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
