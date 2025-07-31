package com.api.certificado.consumer;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.BoletoRequestDTO;
import com.api.certificado.menssaging.SolicitacaoBoletoMenssaging;
import com.api.certificado.service.SolicitacaoCertificadoService;
import com.api.certificado.service.external.RPaymentApiClient;

@Component
public class SolicitacaoBoletoConsumer {

    @Autowired
    private SolicitacaoCertificadoService solicitacaoCertificadoService;

    @Autowired
    private RPaymentApiClient rPaymentApiClient;

    @RabbitListener(queues = "${broker.queue.solicitacao.boleto.name}")
    public void receiveMessage(SolicitacaoBoletoMenssaging request) {
        try {
            Thread.sleep(5000);

            System.out.println("Processando solicitação de boleto: " + request);

            var boletoRequest = new BoletoRequestDTO(
                    request.nome(),
                    request.email(),
                    request.idSolicitacao()
            );

            var boletoResponse = rPaymentApiClient.createBoleto(boletoRequest);

            solicitacaoCertificadoService.updateStatus(
                    request.idSolicitacao(), 
                    StatusSolicitacaoCertificado.BOLETO_EMITIDO
            );

        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Erro ao processar a solicitação de boleto", e);
        }
    }
}
