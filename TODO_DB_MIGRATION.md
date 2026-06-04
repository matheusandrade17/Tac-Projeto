# TODO_DB_MIGRATION.md

- [x] Atualizar dependências (pom.xml) dos serviços: disciplina-service, avaliacao-service e notificacoes para incluir PostgreSQL e remover H2.


- [x] Atualizar `docker/postgres-init.sql` para criar DBs: `tac_disciplina`, `tac_avaliacao`, `tac_notificacoes`.






- [x] Atualizar `application.properties`/`application.yml` dos serviços para apontar para PostgreSQL (URL, driver, user/pass, dialect) e remover parâmetros de H2.


- [ ] Revisar Flyway/DDL no `disciplina-service`, `avaliacao-service` e `notificacoes` para garantir que migrations/DDL funcionem com Postgres.
- [ ] Rodar `docker-compose up -d --build` e validar:
  - [ ] `disciplina-service`, `avaliacao-service`, `notificacoes` conectando em Postgres
  - [ ] healthcheck e ausência de erros de driver H2
  - [ ] tabelas Flyway criadas no schema correto

