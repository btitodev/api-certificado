package com.api.certificado.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.certificado.menssaging.SolicitacaoAgendamentoMenssaging;

@Service
public class SolicitacaoAgendamentoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.solicitacao.agendamento.name}")
    private String queueName;

    public void publish(SolicitacaoAgendamentoMenssaging request) {

        System.out.println("Enviando mensagem de agendamento: " + request);

        rabbitTemplate.convertAndSend(queueName, request);
    }
}
