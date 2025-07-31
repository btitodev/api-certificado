package com.api.certificado.consumer;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.PedidoCompraRequestDTO;
import com.api.certificado.menssaging.SolicitacaoBoletoMenssaging;
import com.api.certificado.menssaging.SolicitacaoCertificadoMenssaging;
import com.api.certificado.producer.SolicitacaoBoletoProducer;
import com.api.certificado.service.SolicitacaoCertificadoService;
import com.api.certificado.service.external.ValidApiClient;

@Component
public class SolicitacaoCertificadoConsumer {

    @Autowired
    private SolicitacaoCertificadoService solicitacaoCertificadoService;

    @Autowired
    private ValidApiClient validApiClient;

    @Autowired
    private SolicitacaoBoletoProducer solicitacaoBoletoProducer;

    @RabbitListener(queues = "${broker.queue.solicitacao.certificado.name}")
    public void receiveMessage(SolicitacaoCertificadoMenssaging request) {
        try {
            Thread.sleep(5000);

            System.out.println("Processando solicitação de certificado: " + request);

            solicitacaoCertificadoService.updateStatus(request.id(), StatusSolicitacaoCertificado.PROCESSANDO);

            var pedidoCompraRequestDTO = new PedidoCompraRequestDTO(request.nome(), request.email());

            var pedidoResponse = validApiClient.createPedidoCompra(pedidoCompraRequestDTO);

            solicitacaoCertificadoService.addTicketNumber(request.id(), pedidoResponse.ticket());

            solicitacaoCertificadoService.updateStatus(request.id(), StatusSolicitacaoCertificado.APROVADO);

            var solicitacaoBoletoMenssaging = new SolicitacaoBoletoMenssaging(
                    request.nome(),
                    request.email(),
                    request.id());

            solicitacaoBoletoProducer.publishMessageSolicitacaoBoleto(solicitacaoBoletoMenssaging);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Erro ao processar a solicitação de certificado", e);
        }

    }

}
