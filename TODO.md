# TODO - Stabilização Tac-Projeto (matricula-service)

## Pendências (prioridade alta)
- [ ] (3) Persistência real: remover listas estáticas/in-memory e unificar controller/service para usar exclusivamente MatriculaRepository (JPA/Postgres).
- [ ] (5) Testcontainers: estabilizar MatriculaIntegrationTest garantindo que Spring ignore RabbitMQ/observabilidade quando roda em testes.

## Já feito
- [x] (1) services/contracts/pom.xml: Java 21 -> 17
- [x] (2) frontend/js/*: substituir base URL para http://localhost:8080/api/v1/
- [x] (4) MatriculaServiceTest com Mockito + AssertJ
- [x] (5) MatriculaIntegrationTest criada/ajustada (falha atual por Docker/observability)

