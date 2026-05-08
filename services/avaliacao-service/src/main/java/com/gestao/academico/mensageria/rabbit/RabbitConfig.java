package com.gestao.academico.mensageria.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Declarables;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "tac.academico.events";

    public static final String QUEUE_AVALIACAO_VALIDACAO = "avaliacao.validacao";

    public static final String RK_ALUNO_CRIADO = "aluno.criado";
    public static final String RK_DISCIPLINA_CRIADA = "disciplina.criada";

    @Bean
    public Declarables declarables() {
        TopicExchange exchange = new TopicExchange(EXCHANGE, true, false);
        Queue queue = new Queue(QUEUE_AVALIACAO_VALIDACAO, true);

        Binding binding1 = BindingBuilder.bind(queue).to(exchange).with(RK_ALUNO_CRIADO);
        Binding binding2 = BindingBuilder.bind(queue).to(exchange).with(RK_DISCIPLINA_CRIADA);

        return new Declarables(exchange, queue, binding1, binding2);
    }
}

