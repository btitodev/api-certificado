package com.api.certificado.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.certificado.menssaging.SolicitacaoBoletoMenssaging;

@Service
public class SolicitacaoBoletoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.solicitacao.boleto.name}")
    private String queueName;

    public void publishMessageSolicitacaoBoleto(SolicitacaoBoletoMenssaging request) {

        System.out.println("Enviando mensagem de boleto: " + request);

        rabbitTemplate.convertAndSend(queueName, request);

    }
}
