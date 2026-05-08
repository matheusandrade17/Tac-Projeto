package com.gestao.academico.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "academico.exchange";
    public static final String ALUNO_CRIADO_ROUTING_KEY = "aluno.criado";
    public static final String MATRICULA_CRIADA_ROUTING_KEY = "matricula.criada";

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

    @Bean
    public Queue matriculaCriadaQueue() {
        return new Queue("matricula.criada.queue", true);
    }

    @Bean
    public Binding matriculaCriadaBinding(Queue matriculaCriadaQueue, TopicExchange academicoExchange) {
        return BindingBuilder.bind(matriculaCriadaQueue).to(academicoExchange).with(MATRICULA_CRIADA_ROUTING_KEY);
    }
}