package com.seuprojeto.matricula;

import com.seuprojeto.contracts.PedidoCriadoEvent;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.MessagePostProcessor;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

        MessagePostProcessor mpp = msg -> {
            MessageProperties props = msg.getMessageProperties();
            props.setMessageId(messageId);
            props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            return msg;
        };

        rabbitTemplate.convertAndSend(ROUTING_KEY, event, mpp);
    }
}
