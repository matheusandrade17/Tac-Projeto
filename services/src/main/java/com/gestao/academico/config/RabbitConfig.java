package com.gestao.academico.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "gestao.exchange";

    public static final String ALUNO_QUEUE = "aluno.queue";
    public static final String ALUNO_ROUTING_KEY = "aluno.criado";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue alunoQueue() {
        return new Queue(ALUNO_QUEUE, true);
    }

    @Bean
    public Binding alunoBinding() {
        return BindingBuilder
                .bind(alunoQueue())
                .to(exchange())
                .with(ALUNO_ROUTING_KEY);
    }
}