## Tac-Projeto

Repositorio ajustado para entrega com um servico Spring Boot simples em memoria.

## O que funciona

- CRUD de alunos em `/api/v1/alunos`
- CRUD de disciplinas em `/api/v1/disciplinas`
- CRUD de matriculas em `/api/v1/matriculas`
- Respostas de erro basicas com `ProblemDetail`
- Publicacao em memoria de evento ao criar matricula

## Como rodar

Pelo Maven:

```powershell
cd services/aluno-service
& "C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.3\plugins\maven\lib\maven3\bin\mvn.cmd" spring-boot:run
```

Ou gerar o jar:

```powershell
cd services/aluno-service
& "C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.3\plugins\maven\lib\maven3\bin\mvn.cmd" -DskipTests package
java -jar target/aluno-service-0.0.1-SNAPSHOT.jar
```

Ou com Docker na raiz:

```powershell
docker compose up --build
```

Aplicacao disponivel em `http://localhost:8081`.

## Exemplos de payload

Aluno:

```json
{
  "id": 1,
  "nome": "Maria",
  "matricula": "2026001"
}
```

Disciplina:

```json
{
  "codigo": "TAC101",
  "nome": "Topicos Avancados",
  "creditos": 4
}
```

Matricula:

```json
{
  "alunoId": "1",
  "disciplinaId": "abc123",
  "dataMatricula": "2026-03-17",
  "status": "MATRICULADO"
}
```
