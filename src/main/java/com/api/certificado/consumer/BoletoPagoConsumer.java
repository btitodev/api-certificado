package com.api.certificado.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.domain.MessagePublisher;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.PedidoCompraRequestDTO;
import com.api.certificado.dto.PedidoCompraResponseDTO;
import com.api.certificado.menssaging.BoletoEmitidoMenssaging;
import com.api.certificado.menssaging.SolicitacaoAgendamentoMenssaging;
import com.api.certificado.service.SolicitacaoCertificadoService;
import com.api.certificado.service.external.ValidApiClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoletoPagoConsumer {

    private final MessagePublisher<SolicitacaoAgendamentoMenssaging> solicitacaoAgendamentoProducer;
    private final ValidApiClient validApiClient;
    private final SolicitacaoCertificadoService solicitacaoCertificadoService;

    @Async
    @RabbitListener(queues = "${broker.queue.boleto.pago.name}")
    public void receiveMessage(BoletoEmitidoMenssaging boletoPagoMessage) {
        try {
            solicitacaoCertificadoService.updateStatus(boletoPagoMessage.idSolicitacao(),
                    StatusSolicitacaoCertificado.BOLETO_EMITIDO);

            log.info("Iniciando processamento de boleto pago para solicitação ID: {}",
                    boletoPagoMessage.idSolicitacao());

            processPayment(boletoPagoMessage);

            log.info("Processamento concluído para boleto ID: {}",
                    boletoPagoMessage.idSolicitacao());
        } catch (Exception ex) {
            log.error("Falha ao processar boleto pago ID: {}",
                    boletoPagoMessage.idSolicitacao(), ex);
            throw ex;
        }
    }

    private void processPayment(BoletoEmitidoMenssaging boletoPagoMessage) {
        var response = processTicketIssuance(boletoPagoMessage);
        requestSchedule(boletoPagoMessage);
    }

    private void requestSchedule(BoletoEmitidoMenssaging boletoPagoMessage) {
        SolicitacaoAgendamentoMenssaging agendamento = criarMensagemAgendamento(boletoPagoMessage);
        solicitacaoAgendamentoProducer.publish(agendamento);

        solicitacaoCertificadoService.updateStatus(boletoPagoMessage.idSolicitacao(),
                StatusSolicitacaoCertificado.AGENDAMENTO_SOLICITADO);
    }

    private PedidoCompraResponseDTO processTicketIssuance(BoletoEmitidoMenssaging message) {
        var request = new PedidoCompraRequestDTO(
                message.nome(),
                message.email(),
                message.idSolicitacao());

        var response = validApiClient.createPedidoCompra(request);
        solicitacaoCertificadoService.updateStatus(message.idSolicitacao(),
                StatusSolicitacaoCertificado.PEDIDO_COMPRA_CONCLUIDO);
        return response;
    }

    private SolicitacaoAgendamentoMenssaging criarMensagemAgendamento(
            BoletoEmitidoMenssaging boletoPagoMessage) {
        return new SolicitacaoAgendamentoMenssaging(
                boletoPagoMessage.nome(),
                boletoPagoMessage.email(),
                boletoPagoMessage.idSolicitacao());
    }
}