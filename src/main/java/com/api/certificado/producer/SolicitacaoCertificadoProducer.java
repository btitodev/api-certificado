package com.api.certificado.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.domain.MessagePublisher;
import com.api.certificado.menssaging.SolicitacaoCertificadoMenssaging;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitacaoCertificadoProducer implements MessagePublisher<SolicitacaoCertificadoMenssaging> {

    private final RabbitTemplate rabbitTemplate;
    
    @Value("${broker.queue.solicitacao.certificado.name}")
    private String queueName;

    @Async
    @Override
    public void publish(SolicitacaoCertificadoMenssaging request) {
        try {
            log.info("Enviando solicitação de certificado - Cliente: {}, ID: {}", 
                    request.nome(), request.id());
            
            publishNextMessage(request);
            
            log.debug("Solicitação de certificado enviada com sucesso - ID: {}", 
                    request.id());
        } catch (Exception ex) {
            log.error("Falha ao enviar solicitação de certificado - ID: {}", 
                    request.id(), ex);
            throw new RuntimeException("Erro na publicação da mensagem de certificado", ex);
        }
    }

    private void publishNextMessage(SolicitacaoCertificadoMenssaging request) {
        rabbitTemplate.convertAndSend(queueName, request);
    }
}