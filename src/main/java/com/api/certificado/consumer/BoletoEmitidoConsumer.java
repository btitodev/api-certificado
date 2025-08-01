package com.api.certificado.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.menssaging.BoletoEmitidoMenssaging;
import com.api.certificado.service.external.CsoApiClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoletoEmitidoConsumer {

    private final CsoApiClient csoApiClient;

    @Async
    @RabbitListener(queues = "${broker.queue.boleto.emitido.name}")
    public void receiveMessage(BoletoEmitidoMenssaging message) {
        try {
            log.info("Iniciando processamento de boleto emitido id: {}", message.idSolicitacao());
            
            processEmittedTicket(message);
            
            log.info("Solicitação processad com sucesso id: {}", message.idSolicitacao());
        } catch (Exception ex) {
            log.error("Failed to process emitted ticket for request ID: {}", message.idSolicitacao(), ex);
            throw ex; // Exception will be handled by RabbitMQ
        }
    }

    private void processEmittedTicket(BoletoEmitidoMenssaging message) {
        // Uncomment in production:
        // csoApiClient.sendBoletoEmitido(message);
        log.debug("Simulating external API call for request ID: {}", message.idSolicitacao());
        
        // Additional processing logic can be added here
    }
}