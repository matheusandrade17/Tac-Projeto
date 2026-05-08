package com.gestao.academico.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "academico.exchange";
    public static final String MATRICULA_CRIADA_ROUTING_KEY = "matricula.criada";

    @Bean
    public TopicExchange academicoExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue matriculaCriadaQueue() {
        return new Queue("matricula.criada.queue", true);
    }

    @Bean
    public Binding matriculaCriadaBinding(Queue matriculaCriadaQueue, TopicExchange academicoExchange) {
        return BindingBuilder.bind(matriculaCriadaQueue).to(academicoExchange).with(MATRICULA_CRIADA_ROUTING_KEY);
    }
}