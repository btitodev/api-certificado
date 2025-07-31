package com.api.certificado.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.PedidoCompraRequestDTO;
import com.api.certificado.menssaging.SolicitacaoCertificadoMenssaging;
import com.api.certificado.service.SolicitacaoCertificadoService;
import com.api.certificado.service.external.ValidApiClient;

@Component
public class SolicitacaoCertificadoConsumer {

    @Autowired
    private SolicitacaoCertificadoService solicitacaoCertificadoService;

    @Autowired
    private ValidApiClient validApiClient;

    @RabbitListener(queues = "${broker.queue.solicitacao.name}")
    public void receiveMessage(SolicitacaoCertificadoMenssaging request) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        solicitacaoCertificadoService.updateStatus(request.id(), StatusSolicitacaoCertificado.PROCESSANDO);

        var pedidoCompraRequestDTO = new PedidoCompraRequestDTO(request.nome(), request.email());

        var pedidoResponse = validApiClient.createPedidoCompra(pedidoCompraRequestDTO);

        solicitacaoCertificadoService.updateStatus(request.id(), StatusSolicitacaoCertificado.APROVADO);
    }

}
