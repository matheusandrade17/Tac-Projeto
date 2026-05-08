# TODO - Execução Mensageria RabbitMQ (Arquitetura 100% orientada a eventos)

## Visão geral (meta)
- Remover criação/validação síncrona de matrícula e avaliação via controller.
- Matricula e Avaliação passam a ser processadas por **listeners** (consumidores) via RabbitMQ.
- Implementar:
  - @EnableRabbit
  - @RabbitListener tipado
  - Jackson2JsonMessageConverter
  - MessageConverter explícito
  - retry/backoff
  - DLQ (dead letter queue) com exchanges/queues dedicadas
  - logs estruturados com correlationId/eventId
- Manter compatibilidade Java 17 + Spring Boot 3 + Docker Compose.

## Passo a passo (o que será alterado)
### 1) Padronização Rabbit (matricula-service)
- [ ] Criar config de mensageria: `RabbitMessagingConfig` com:
  - [ ] `Jackson2JsonMessageConverter`
  - [ ] `RabbitListenerContainerFactory` com retry e DLQ
- [ ] Atualizar `RabbitConfig` para declarar queues/bindings/dlq (ou criar config adicional).

### 2) Padronização Rabbit (avaliacao-service)
- [ ] Criar config de mensageria: `RabbitMessagingConfig` com:
  - [ ] `Jackson2JsonMessageConverter`
  - [ ] `RabbitListenerContainerFactory` com retry e DLQ
- [ ] Atualizar `RabbitConfig` para declarar queues/bindings/dlq (ou criar config adicional).

### 3) Criar novos eventos de comando
- [ ] Criar DTO (em cada serviço onde fizer sentido ou em um pacote comum do serviço):
  - `MatriculaSolicitadaEvent` (command)
  - `AvaliacaoSolicitadaEvent` (command)

### 4) Consumidores reais e lógica de negócio (matricula-service)
- [ ] Substituir `ValidacaoListener` por:
  - listener de validação por eventos `AlunoCriadoEvent` e `DisciplinaCriadaEvent` (cache local para validar de forma assíncrona)
  - listener de comando `MatriculaSolicitadaEvent` para:
    - [ ] validar IDs contra cache
    - [ ] persistir `Matricula` (JPA)
    - [ ] publicar `MatriculaCriadaEvent` em `matricula.criada`

### 5) Consumidores reais e lógica de negócio (avaliacao-service)
- [ ] Substituir `ValidacaoListener` por:
  - listener de validação por eventos `AlunoCriadoEvent` e `DisciplinaCriadaEvent`
  - listener de comando `AvaliacaoSolicitadaEvent` para:
    - [ ] validar IDs contra cache
    - [ ] persistir `Avaliacao` (JPA)
    - [ ] publicar `AvaliacaoCriadaEvent` em `avaliacao.criada`

### 6) Publicação via controllers (gateway do fluxo assíncrono)
- [ ] Ajustar `MatriculaController` para publicar `MatriculaSolicitadaEvent` no POST.
- [ ] Ajustar `AvaliacaoController` para publicar `AvaliacaoSolicitadaEvent` no POST.
- [ ] Resposta do controller: retornar 202 (aceito/processando) e um id/eventId de correlação.

### 7) Remover RestTemplate onde não for necessário
- [ ] Remover/ignorar `RestTemplateConfig` em matricula/avaliacao se não houver uso.

### 8) Retry, erro e DLQ
- [ ] Configurar retry/backoff no container factory.
- [ ] Configurar DLQ e routing para mensagens que falharem.
- [ ] Logging estruturado por tentativa + exception.

### 9) Testes
- [ ] Atualizar tests de integração/unit para refletir fluxo por eventos.

### 10) Verificação final
- [ ] docker compose up -d --build
- [ ] Validar RabbitMQ UI:
  - [ ] queues e mensagens
  - [ ] DLQ vazia no cenário feliz
  - [ ] DLQ preenchida no cenário de erro proposital.

## Progresso
- [x] Planejamento revisado
- [x] Implementação iniciada

## Histórico (implementação)
- [ ] Aplicar mudanças em matricula-service (config JSON/retry/DLQ + listeners de criação)


