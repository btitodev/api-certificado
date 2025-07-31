package com.api.certificado.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.certificado.domain.solicitacaoCertificado.MessagePublisher;
import com.api.certificado.menssaging.SolicitacaoCertificadoMenssaging;


@Service
public class SolicitacaoCertificadoProducer implements MessagePublisher<SolicitacaoCertificadoMenssaging> {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.solicitacao.certificado.name}")
    private String queueName;

    public void publish(SolicitacaoCertificadoMenssaging request) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Enviando mensagem de solicitação de certificado: " + request);

        rabbitTemplate.convertAndSend(queueName, request);
    }
}
