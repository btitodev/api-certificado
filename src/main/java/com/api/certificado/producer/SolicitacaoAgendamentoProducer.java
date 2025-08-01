package com.api.certificado.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.domain.MessagePublisher;
import com.api.certificado.menssaging.SolicitacaoAgendamentoMenssaging;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitacaoAgendamentoProducer implements MessagePublisher<SolicitacaoAgendamentoMenssaging> {

    private final RabbitTemplate rabbitTemplate;
    
    @Value("${broker.queue.solicitacao.agendamento.name}")
    private String queueName;

    @Async
    public void publish(SolicitacaoAgendamentoMenssaging request) {
        try {
            log.info("Enviando mensagem de agendamento para solicitação ID: {}", request.idSolicitacao());
            
            publishNextMessage(request);
            
            log.debug("Mensagem de agendamento enviada com sucesso para ID: {}", request.idSolicitacao());
        } catch (Exception ex) {
            log.error("Falha ao enviar mensagem de agendamento para ID: {}", request.idSolicitacao(), ex);
            throw new RuntimeException("Erro ao publicar mensagem na fila", ex);
        }
    }

    private void publishNextMessage(SolicitacaoAgendamentoMenssaging request) {
        rabbitTemplate.convertAndSend(queueName, request);
    }
}