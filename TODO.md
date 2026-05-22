# Observabilidade - TODO

- [ ] Atualizar POMs (Actuator + Micrometer Prometheus + Brave tracing) nos serviços:
  - [x] aluno-service
  - [ ] disciplina-service
  - [ ] matricula-service
  - [ ] avaliacao-service
  - [ ] gateway
  - [ ] notificacoes




- [ ] Atualizar configurações por serviço (Actuator/Prometheus/Tracing/MDC) em `application.yml`/`application.properties`.
  - [ ] aluno-service
  - [ ] disciplina-service
  - [ ] matricula-service
  - [ ] avaliacao-service
  - [ ] gateway
  - [ ] notificacoes
- [ ] Atualizar `docker-compose.yml` para incluir Prometheus (9090) e Grafana (3000) + mounts.
- [x] Criar `docker/prometheus/prometheus.yml`.
- [x] Criar `docker/grafana/datasources/*` e `docker/grafana/dashboards/*` (incluindo README placeholder).

- [ ] Build & validação via Docker Compose.

