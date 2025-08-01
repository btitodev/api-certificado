package com.api.certificado.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.menssaging.BoletoEmitidoMenssaging;
import com.api.certificado.menssaging.SolicitacaoAgendamentoMenssaging;
import com.api.certificado.producer.SolicitacaoAgendamentoProducer;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoletoPagoConsumer {

    private final SolicitacaoAgendamentoProducer solicitacaoAgendamentoProducer;

    @Async
    @RabbitListener(queues = "${broker.queue.boleto.pago.name}")
    public void receiveMessage(BoletoEmitidoMenssaging boletoPagoMessage) {
        try {
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
        SolicitacaoAgendamentoMenssaging agendamento = criarMensagemAgendamento(boletoPagoMessage);
        solicitacaoAgendamentoProducer.publish(agendamento);
        
        log.debug("Mensagem de agendamento publicada para ID: {}", 
                boletoPagoMessage.idSolicitacao());
    }

    private SolicitacaoAgendamentoMenssaging criarMensagemAgendamento(
            BoletoEmitidoMenssaging boletoPagoMessage) {
        return new SolicitacaoAgendamentoMenssaging(
            boletoPagoMessage.nome(),
            boletoPagoMessage.email(),
            boletoPagoMessage.idSolicitacao()
        );
    }
}