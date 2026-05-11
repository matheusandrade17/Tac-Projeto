@Component
public class PedidoProjector {

    private final JdbcTemplate jdbc;
    private final ProcessedEventsRepository processedEvents;
    private final PlatformTransactionManager txManager;
    private final Logger logger = LoggerFactory.getLogger(PedidoProjector.class);

    public PedidoProjector(JdbcTemplate jdbc,
                           ProcessedEventsRepository processedEvents,
                           PlatformTransactionManager txManager) {
        this.jdbc = jdbc;
        this.processedEvents = processedEvents;
        this.txManager = txManager;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.pedido-criado:pedido.criado}")
    public void handle(Message message) {
        String msgId = message.getMessageProperties().getMessageId();
        String payload = new String(message.getBody(), StandardCharsets.UTF_8);

        if (msgId == null) {
            logger.warn("Mensagem sem messageId recebida, ignorando");
            return;
        }

        if (processedEvents.exists(msgId)) {
            logger.warn("Evento {} já processado - ignorando", msgId);
            return;
        }

        TransactionTemplate tx = new TransactionTemplate(txManager);
        tx.execute(status -> {
            try {
                // parse payload para objeto (use Jackson)
                PedidoCriadoEvent evt = new ObjectMapper().readValue(payload, PedidoCriadoEvent.class);

                // inserir pedido no read model
                jdbc.update(
                        "INSERT INTO pedidos_readmodel(pedido_id, cliente_id, nome_cliente, email_cliente, status, valor_total, criado_em) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        evt.getPedidoId(), evt.getClienteId(), evt.getNomeCliente(), evt.getEmailCliente(), "Criado", evt.getValorTotal(), evt.getCriadoEm()
                );

                // inserir itens
                for (ItemEvent item : evt.getItens()) {
                    jdbc.update(
                            "INSERT INTO itens_readmodel(pedido_id, nome_produto, quantidade, preco_unitario, subtotal) VALUES (?, ?, ?, ?, ?)",
                            evt.getPedidoId(), item.getNomeProduto(), item.getQuantidade(), item.getPrecoUnitario(), item.getQuantidade().multiply(item.getPrecoUnitario())
                    );
                }

                // registrar evento processado
                processedEvents.register(msgId);

                logger.info("Read Model atualizado para Pedido {}", evt.getPedidoId());
            } catch (Exception ex) {
                logger.error("Erro ao projetar evento {}: {}", msgId, ex.getMessage(), ex);
                throw new RuntimeException(ex);
            }
            return null;
        });
    }
}
