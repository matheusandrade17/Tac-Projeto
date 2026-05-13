package com.gestao.academico.notificacoes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seuprojeto.contracts.ItemDto;
import com.seuprojeto.contracts.PedidoCriadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.nio.charset.StandardCharsets;

@Component
public class PedidoProjector {
    private final JdbcTemplate jdbc;
    private final ProcessedEventsRepository processedEvents;
    private final PlatformTransactionManager txManager;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(PedidoProjector.class);

    public PedidoProjector(JdbcTemplate jdbc, ProcessedEventsRepository processedEvents, PlatformTransactionManager txManager, ObjectMapper objectMapper) {
        this.jdbc = jdbc;
        this.processedEvents = processedEvents;
        this.txManager = txManager;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "pedido.criado")
    public void handle(Message message) {
        String msgId = message.getMessageProperties().getMessageId();
        if (msgId == null) msgId = (String) message.getMessageProperties().getHeaders().get("x-message-id");
        if (msgId == null || processedEvents.exists(msgId)) return;

        String payload = new String(message.getBody(), StandardCharsets.UTF_8);
        TransactionTemplate tx = new TransactionTemplate(txManager);
        final String finalMsgId = msgId;
        tx.execute(status -> {
            try {
                PedidoCriadoEvent evt = objectMapper.readValue(payload, PedidoCriadoEvent.class);
                jdbc.update("INSERT INTO pedidos_readmodel(pedido_id, cliente_id, nome_cliente, email_cliente, status, valor_total, criado_em) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        evt.getPedidoId(), evt.getClienteId(), evt.getNomeCliente(), evt.getEmailCliente(), "CRIADO", evt.getValorTotal(), evt.getCriadoEm());
                for (ItemDto item : evt.getItens()) {
                    jdbc.update("INSERT INTO itens_readmodel(pedido_id, nome_produto, quantidade, preco_unitario, subtotal) VALUES (?, ?, ?, ?, ?)",
                            evt.getPedidoId(), item.getNomeProduto(), item.getQuantidade(), item.getPrecoUnitario(), item.getQuantidade().multiply(item.getPrecoUnitario()));
                }
                processedEvents.register(finalMsgId);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return null;
        });
    }
}
