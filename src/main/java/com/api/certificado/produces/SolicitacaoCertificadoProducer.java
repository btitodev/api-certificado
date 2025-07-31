package com.api.certificado.produces;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificadoRequestDTO;

@Component 
public class SolicitacaoCertificadoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.solicitacao.name}")
    private String queueName;

    public void publishMessageSolicitacaocertificado(SolicitacaoCertificadoRequestDTO request) {
        rabbitTemplate.convertAndSend(queueName, request);
    }
}
