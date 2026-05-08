# TODO - Comunicação entre Serviços (Matricula & Avaliacao)

- [ ] Ajustar `matricula-service` para chamar `aluno-service` e `disciplina-service` usando URLs docker-friendly vindas de `application.properties`.
- [ ] Implementar validação `disciplinaId` no `MatriculaService` (além da validação de `alunoId`).
- [ ] Implementar validação `alunoId` e `disciplinaId` no `avaliacao-service` antes de salvar.
- [ ] Adicionar `RestTemplate` no `avaliacao-service` (via `RestTemplateConfig` ou bean equivalente).
- [ ] Criar propriedades em `matricula-service` e `avaliacao-service` com base URLs (ex.: `http://aluno-service:8081/...`).
- [ ] Subir todos os serviços com `docker compose up -d --build`.
- [ ] Testar fluxo completo:
  - criar aluno em `aluno-service`
  - criar disciplina em `disciplina-service`
  - criar matrícula em `matricula-service`
  - criar avaliação em `avaliacao-service`
- [ ] Testar cenário de erro:
  - criar matrícula/avaliação com `alunoId` inexistente e confirmar erro.
- [ ] Testar cenário de erro:
  - criar matrícula/avaliação com `disciplinaId` inexistente e confirmar erro.

