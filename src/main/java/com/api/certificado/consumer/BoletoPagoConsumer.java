package com.api.certificado.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.certificado.menssaging.BoletoEmitidoMenssaging;
import com.api.certificado.menssaging.SolicitacaoAgendamentoMenssaging;
import com.api.certificado.producer.SolicitacaoAgendamentoProducer;

@Component
public class BoletoPagoConsumer {

    @Autowired
    private SolicitacaoAgendamentoProducer solicitacaoAgendamentoProducer;

    @RabbitListener(queues = "${broker.queue.boleto.pago.name}")
    public void receiveMessage(BoletoEmitidoMenssaging boletoPagoMessage) {
        System.out.println("Processando boleto pago: " + boletoPagoMessage);

        SolicitacaoAgendamentoMenssaging agendamento = new SolicitacaoAgendamentoMenssaging(
                boletoPagoMessage.nome(),
                boletoPagoMessage.email(),
                boletoPagoMessage.idSolicitacao());

        solicitacaoAgendamentoProducer.publish(agendamento);
    }
}
