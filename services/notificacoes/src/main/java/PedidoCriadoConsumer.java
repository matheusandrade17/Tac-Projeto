import com.seuprojeto.contracts.PedidoCriadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoCriadoConsumer {

    private final JdbcTemplate jdbc;
    private final Logger logger = LoggerFactory.getLogger(PedidoCriadoConsumer.class);

    public PedidoCriadoConsumer(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        // cria tabela de idempotência se não existir
        jdbc.execute("CREATE TABLE IF NOT EXISTS processed_events (message_id VARCHAR(100) PRIMARY KEY, processed_at TIMESTAMP)");
    }

    @RabbitListener(queues = "pedido.criado")
    public void handle(PedidoCriadoEvent event, Message message) {
        String messageId = Optional.ofNullable(message.getMessageProperties().getMessageId())
                .orElse(UUID.randomUUID().toString());

        Integer exists = jdbc.queryForObject(
                "SELECT COUNT(1) FROM processed_events WHERE message_id = ?",
                Integer.class, messageId);

        if (exists != null && exists > 0) {
            logger.warn("Evento {} já processado - ignorando", messageId);
            return;
        }

        // simula envio de e-mail
        logger.info("[E-MAIL SIMULADO] Para: {} | Pedido: {} | Total: {}",
                event.emailCliente(), event.pedidoId(), event.valorTotal());

        // registra como processado
        jdbc.update("INSERT INTO processed_events(message_id, processed_at) VALUES (?, ?)",
                messageId, Timestamp.from(Instant.now()));
    }
}
