package com.api.certificado.consumer;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.menssaging.BoletoEmitidoMenssaging;
import com.api.certificado.service.SolicitacaoCertificadoService;
import com.api.certificado.service.external.CsoApiClient;

@Component
public class BoletoEmitidoConsumer {

    @Autowired
    private CsoApiClient csoApiClient;

    @Autowired
    private SolicitacaoCertificadoService solicitacaoCertificadoService;

    @RabbitListener(queues = "${broker.queue.boleto.emitido.name}")
    public void receiveMessage(BoletoEmitidoMenssaging message) {
        try {

            System.out.println("Processando mensagem de boleto emitido: " + message);

            csoApiClient.sendBoletoEmitido(message);

            solicitacaoCertificadoService.updateStatus(
                    message.idSolicitacao(),
                    StatusSolicitacaoCertificado.BOLETO_ENVIADO
            );

            System.out.println("Boleto enviado com sucesso para a solicitação: " + message.idSolicitacao());

          } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Erro ao processar a mensagem", e);
        }
    }
}
