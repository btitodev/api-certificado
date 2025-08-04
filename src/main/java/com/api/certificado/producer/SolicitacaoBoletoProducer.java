package com.api.certificado.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.domain.MessagePublisher;
import com.api.certificado.menssaging.SolicitacaoBoletoMenssaging;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitacaoBoletoProducer implements MessagePublisher<SolicitacaoBoletoMenssaging> {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.solicitacao.boleto.name}")
    private String queueName;

    @Async
    @Override
    public void publish(SolicitacaoBoletoMenssaging request) {
        try {
            log.info("Enviando solicitação de boleto para o cliente: {}, ID: {}", 
            request.idSolicitacao());

            publishNextMessage(request);

            log.debug("Solicitação de boleto enviada com sucesso para ID: {}",
                    request.idSolicitacao());
        } catch (Exception ex) {
            log.error("Falha ao enviar solicitação de boleto para ID: {}",
                    request.idSolicitacao(), ex);
            throw new RuntimeException("Erro ao publicar mensagem na fila de boletos", ex);
        }
    }

    private void publishNextMessage(SolicitacaoBoletoMenssaging request) {
        rabbitTemplate.convertAndSend(queueName, request);
    }
}