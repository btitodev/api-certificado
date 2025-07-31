package com.api.certificado.config;

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
    @Value("${broker.queue.solicitacao.certificado.name}")
    private String certificadoQueue;

    @Value("${broker.queue.solicitacao.boleto.name}")
    private String boletoQueue;

    @Value("${broker.queue.solicitacao.agendamento.name}")
    private String agendamentoQueue;

    // Filas DLQ
    @Value("${broker.queue.solicitacao.dlq.name}")
    private String certificadoDlqQueue;

    @Value("${broker.queue.solicitacao.boleto.dlq.name}")
    private String boletoDlqQueue;

    @Value("${broker.queue.solicitacao.agendamento.dlq.name}")
    private String agendamentoDlqQueue;

    // Exchanges DLQ centralizadas
    @Value("${broker.exchange.dlq.certificado}")
    private String certificadoDlx;

    @Value("${broker.exchange.dlq.boleto}")
    private String boletoDlx;

    @Value("${broker.exchange.dlq.agendamento}")
    private String agendamentoDlx;

   // ==================== Fila principal: CERTIFICADO ====================
    @Bean
    public Queue certificadoQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", certificadoDlx);
        args.put("x-dead-letter-routing-key", certificadoDlqQueue);
        return new Queue(certificadoQueue, true, false, false, args);
    }

    @Bean
    public Queue certificadoDlq() {
        return new Queue(certificadoDlqQueue, true);
    }

    @Bean
    public DirectExchange certificadoDlxExchange() {
        return new DirectExchange(certificadoDlx);
    }

    @Bean
    public Binding certificadoDlqBinding() {
        return BindingBuilder.bind(certificadoDlq())
                .to(certificadoDlxExchange())
                .with(certificadoDlqQueue);
    }

    // ==================== Fila principal: BOLETO ====================
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
    public DirectExchange boletoDlxExchange() {
        return new DirectExchange(boletoDlx);
    }

    @Bean
    public Binding boletoDlqBinding() {
        return BindingBuilder.bind(boletoDlq())
                .to(boletoDlxExchange())
                .with(boletoDlqQueue);
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

}
