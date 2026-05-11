import com.seuprojeto.contracts.PedidoCriadoEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final String ROUTING_KEY = "pedido.criado";

    public EventoPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    private void init() {
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    public void publicar(PedidoCriadoEvent event) {

        String messageId = UUID.randomUUID().toString();

        MessagePostProcessor mpp = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message msg) throws AmqpException {

                MessageProperties props = msg.getMessageProperties();
                props.setMessageId(messageId);
                props.setContentType(MessageProperties.CONTENT_TYPE_JSON);

                return msg;
            }
        };

        rabbitTemplate.convertAndSend("", ROUTING_KEY, event, mpp);
    }
}