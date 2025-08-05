package com.api.certificado.config.rabbitMQ;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQQueueConfig {

    // Filas principais
    @Value("${broker.queue.solicitacao.boleto.name}")
    private String boletoSolicitacaoQueue;

    @Value("${broker.queue.solicitacao.agendamento.name}")
    private String agendamentoQueue;

    @Value("${broker.queue.boleto.name}")
    private String boletoQueue;

    // Filas DLQ
    @Value("${broker.queue.solicitacao.boleto.dlq.name}")
    private String boletoSolicitacaoDlqQueue;

    @Value("${broker.queue.solicitacao.agendamento.dlq.name}")
    private String agendamentoDlqQueue;

    @Value("${broker.queue.boleto.dlq.name}")
    private String boletoDlqQueue;

    // Exchanges DLQ centralizadas
    @Value("${broker.exchange.dlq.boleto}")
    private String boletoDlx;

    @Value("${broker.exchange.dlq.agendamento}")
    private String agendamentoDlx;

    // ==================== Fila principal: SOLICITACAO BOLETO ====================
    @Bean
    public Queue boletoSolicitacaoQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", boletoDlx);
        args.put("x-dead-letter-routing-key", boletoSolicitacaoDlqQueue);
        return new Queue(boletoSolicitacaoQueue, true, false, false, args);
    }

    @Bean
    public Queue boletoSolicitacaoDlq() {
        return new Queue(boletoSolicitacaoDlqQueue, true);
    }

    @Bean
    public DirectExchange boletoDlxExchange() {
        return new DirectExchange(boletoDlx);
    }

    @Bean
    public Binding boletoSolicitacaoDlqBinding() {
        return BindingBuilder.bind(boletoSolicitacaoDlq())
                .to(boletoDlxExchange())
                .with(boletoSolicitacaoDlqQueue);
    }

    // ==================== Fila principal: AGENDAMENTO ====================
    @Bean
    public Queue agendamentoQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", agendamentoDlx);
        args.put("x-dead-letter-routing-key", agendamentoDlqQueue);
        return new Queue(agendamentoQueue, true, false, false, args);
    }

    @Bean
    public Queue agendamentoDlq() {
        return new Queue(agendamentoDlqQueue, true);
    }

    @Bean
    public DirectExchange agendamentoDlxExchange() {
        return new DirectExchange(agendamentoDlx);
    }

    @Bean
    public Binding agendamentoDlqBinding() {
        return BindingBuilder.bind(agendamentoDlq())
                .to(agendamentoDlxExchange())
                .with(agendamentoDlqQueue);
    }

    // ==================== Fila unificada: BOLETO ====================
    @Bean
    public Queue boletoQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", boletoDlx);
        args.put("x-dead-letter-routing-key", boletoDlqQueue);
        return new Queue(boletoQueue, true, false, false, args);
    }

    @Bean
    public Queue boletoDlq() {
        return new Queue(boletoDlqQueue, true);
    }

    @Bean
    public Binding boletoDlqBinding() {
        return BindingBuilder.bind(boletoDlq())
                .to(boletoDlxExchange())
                .with(boletoDlqQueue);
    }
}
