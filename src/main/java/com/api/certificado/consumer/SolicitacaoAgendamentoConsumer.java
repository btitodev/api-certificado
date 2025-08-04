package com.api.certificado.consumer;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.controller.dto.agendamento.AgendamentoRequestDTO;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.menssaging.SolicitacaoAgendamentoMenssaging;
import com.api.certificado.service.SolicitacaoCertificadoService;
import com.api.certificado.service.external.ValidApiClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class SolicitacaoAgendamentoConsumer {

    private final SolicitacaoCertificadoService solicitacaoCertificadoService;
    private final ValidApiClient validApiClient;

    @Async
    @RabbitListener(queues = "${broker.queue.solicitacao.agendamento.name}")
    @Transactional
    public void receiveMessage(SolicitacaoAgendamentoMenssaging request) {
        try {
            log.info("Iniciando processamento de agendamento para solicitação ID: {}", request.idSolicitacao());
            
            processScheduling(request);
            
            log.info("Agendamento concluído com sucesso para ID: {}", request.idSolicitacao());
        } catch (Exception ex) {
            log.error("Falha ao processar agendamento para ID: {}", request.idSolicitacao(), ex);
            throw new AmqpRejectAndDontRequeueException("Erro no processamento de agendamento");
        }
    }

    private void processScheduling(SolicitacaoAgendamentoMenssaging request) {
        // 1. Criar DTO para a API externa
        AgendamentoRequestDTO agendamentoRequest = new AgendamentoRequestDTO(
            request.nome(),
            request.email(),
            request.idSolicitacao()
        );

        // 2. Chamar API externa (descomentar em produção)
        // validApiClient.createAgendamento(agendamentoRequest);
        log.debug("Simulando chamada à API de agendamento para ID: {}", request.idSolicitacao());

        // 3. Atualizar status no banco de dados
        solicitacaoCertificadoService.updateStatus(
            request.idSolicitacao(),
            StatusSolicitacaoCertificado.AGENDADO
        );
    }
}