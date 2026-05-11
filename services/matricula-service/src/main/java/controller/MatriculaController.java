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
