package com.api.certificado.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.menssaging.SolicitacaoBoletoMenssaging;
import com.api.certificado.service.SolicitacaoCertificadoService;

@Service
public class SolicitacaoBoletoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.solicitacao.boleto.name}")
    private String queueName;

    public void publishMessageSolicitacaoBoleto(SolicitacaoBoletoMenssaging request) {
        // aguardar 5 segundos antes de enviar a mensagem
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        rabbitTemplate.convertAndSend(queueName, request);

    }
}
