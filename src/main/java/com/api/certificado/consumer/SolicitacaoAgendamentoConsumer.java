package com.api.certificado.consumer;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.AgendamentoRequestDTO;
import com.api.certificado.menssaging.SolicitacaoAgendamentoMenssaging;
import com.api.certificado.service.SolicitacaoCertificadoService;
import com.api.certificado.service.external.ValidApiClient;

@Component
public class SolicitacaoAgendamentoConsumer {

    @Autowired
    private SolicitacaoCertificadoService solicitacaoCertificadoService;

    @Autowired
    private ValidApiClient validApiClient;

    @RabbitListener(queues = "${broker.queue.solicitacao.agendamento.name}")
    public void receiveMessage(SolicitacaoAgendamentoMenssaging request) {
        try {
            Thread.sleep(5000); 

            System.out.println("Processando solicitação de agendamento: " + request);

            AgendamentoRequestDTO agendamentoRequestDTO = new AgendamentoRequestDTO(
                    request.nome(),
                    request.email(),
                    request.idSolicitacao());

            validApiClient.createAgendamento(agendamentoRequestDTO);

            solicitacaoCertificadoService.updateStatus(
                    request.idSolicitacao(), 
                    StatusSolicitacaoCertificado.APROVADO
            );

        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Erro ao processar a mensagem", e);
        }
    }
}
