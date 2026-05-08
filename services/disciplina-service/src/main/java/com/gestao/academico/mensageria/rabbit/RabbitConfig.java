package com.gestao.academico.mensageria.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "tac.academico.events";

    // Não é necessário queue no disciplina-service para publicar;
    // mas mantemos routing keys comuns para padronizar.
    public static final String QUEUE_MATRICULA_VALIDACAO = "matricula.validacao";
    public static final String QUEUE_AVALIACAO_VALIDACAO = "avaliacao.validacao";

    public static final String RK_ALUNO_CRIADO = "aluno.criado";
    public static final String RK_DISCIPLINA_CRIADA = "disciplina.criada";

    @Bean
    public Declarables declarables() {
        TopicExchange exchange = new TopicExchange(EXCHANGE, true, false);

        Queue queueMatricula = new Queue(QUEUE_MATRICULA_VALIDACAO, true);
        Queue queueAvaliacao = new Queue(QUEUE_AVALIACAO_VALIDACAO, true);

        Binding binding1 = BindingBuilder.bind(queueMatricula).to(exchange).with(RK_ALUNO_CRIADO);
        Binding binding2 = BindingBuilder.bind(queueMatricula).to(exchange).with(RK_DISCIPLINA_CRIADA);
        Binding binding3 = BindingBuilder.bind(queueAvaliacao).to(exchange).with(RK_ALUNO_CRIADO);
        Binding binding4 = BindingBuilder.bind(queueAvaliacao).to(exchange).with(RK_DISCIPLINA_CRIADA);

        return new Declarables(exchange, queueMatricula, queueAvaliacao, binding1, binding2, binding3, binding4);
    }
}

