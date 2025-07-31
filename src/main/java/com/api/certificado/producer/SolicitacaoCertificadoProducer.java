package com.api.certificado.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.api.certificado.menssaging.SolicitacaoCertificadoMenssaging;

@Component
public class SolicitacaoCertificadoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.solicitacao.name}")
    private String queueName;

    public void publishMessageSolicitacaocertificado(SolicitacaoCertificadoMenssaging request) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Enviando mensagem de solicitação de certificado: " + request);

        rabbitTemplate.convertAndSend(queueName, request);
    }
}
