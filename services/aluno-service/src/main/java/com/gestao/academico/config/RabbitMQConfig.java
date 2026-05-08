package com.gestao.academico.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "academico.exchange";
    public static final String ALUNO_CRIADO_ROUTING_KEY = "aluno.criado";

    @Bean
    public TopicExchange academicoExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue alunoCriadoQueue() {
        return new Queue("aluno.criado.queue", true);
    }

    @Bean
    public Binding alunoCriadoBinding(Queue alunoCriadoQueue, TopicExchange academicoExchange) {
        return BindingBuilder.bind(alunoCriadoQueue).to(academicoExchange).with(ALUNO_CRIADO_ROUTING_KEY);
    }
}