package com.api.certificado.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.menssaging.SolicitacaoBoletoMenssaging;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitacaoBoletoProducer {

    private final RabbitTemplate rabbitTemplate;
    
    @Value("${broker.queue.solicitacao.boleto.name}")
    private String queueName;

    @Async
    public void publishMessageSolicitacaoBoleto(SolicitacaoBoletoMenssaging request) {
        try {
            log.info("Enviando solicitação de boleto para o cliente: {}, ID: {}", 
                    request.nome(), request.idSolicitacao());
            
            sendMessageToQueue(request);
            
            log.debug("Solicitação de boleto enviada com sucesso para ID: {}", 
                    request.idSolicitacao());
        } catch (Exception ex) {
            log.error("Falha ao enviar solicitação de boleto para ID: {}", 
                    request.idSolicitacao(), ex);
            throw new RuntimeException("Erro ao publicar mensagem na fila de boletos", ex);
        }
    }

    private void sendMessageToQueue(SolicitacaoBoletoMenssaging request) {
        rabbitTemplate.convertAndSend(queueName, request);
    }
}