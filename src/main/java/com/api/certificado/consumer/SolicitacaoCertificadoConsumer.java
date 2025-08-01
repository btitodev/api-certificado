package com.api.certificado.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.PedidoCompraRequestDTO;
import com.api.certificado.dto.PedidoCompraResponseDTO;
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
        System.out.println("Processando solicitação de certificado: " + request);

        var pedidoCompraRequestDTO = new PedidoCompraRequestDTO(request.nome(), request.email(), request.id());

        //mock
        var pedidoResponse = new PedidoCompraResponseDTO("1234567890");

        //var pedidoResponse = validApiClient.createPedidoCompra(pedidoCompraRequestDTO);

        //aguardar 5 segundos
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        solicitacaoCertificadoService.addTicketNumber(request.id(), pedidoResponse.ticket());

        solicitacaoCertificadoService.updateStatus(request.id(),
                StatusSolicitacaoCertificado.PEDIDO_COMPRA_CONCLUIDO);

        var solicitacaoBoletoMenssaging = new SolicitacaoBoletoMenssaging(
                request.nome(),
                request.email(),
                request.id());

        solicitacaoBoletoProducer.publishMessageSolicitacaoBoleto(solicitacaoBoletoMenssaging);
        System.out.println("Solicitação de certificado processada com sucesso: " + request.id());

    }

}
