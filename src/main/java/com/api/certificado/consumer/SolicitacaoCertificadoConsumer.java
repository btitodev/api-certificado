package com.api.certificado.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.PedidoCompraResponseDTO;
import com.api.certificado.menssaging.SolicitacaoBoletoMenssaging;
import com.api.certificado.menssaging.SolicitacaoCertificadoMenssaging;
import com.api.certificado.producer.SolicitacaoBoletoProducer;
import com.api.certificado.service.SolicitacaoCertificadoService;
import com.api.certificado.service.external.ValidApiClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class SolicitacaoCertificadoConsumer {

    private final SolicitacaoCertificadoService solicitacaoCertificadoService;
    private final ValidApiClient validApiClient;
    private final SolicitacaoBoletoProducer solicitacaoBoletoProducer;

    @Async
    @RabbitListener(queues = "${broker.queue.solicitacao.certificado.name}")
    @Transactional
    public void receiveMessage(SolicitacaoCertificadoMenssaging request) {
        try {
            log.info("Processando solicitação de certificado ID: {}", request.id());
            
            // 1. Cria pedido de compra
            var pedidoResponse = createPedidoCompra(request);
            
            // 2. Atualiza status
            updateStatusAndTicket(request, pedidoResponse);
            
            // 3. Publica próxima mensagem
            publishNextMessage(request);
            
            log.info("Processamento concluído para ID: {}", request.id());
        } catch (Exception ex) {
            log.error("Falha ao processar solicitação ID: {}", request.id(), ex);
            throw ex; // Será tratado pelo RabbitMQ (pode configurar DLQ)
        }
    }

    private PedidoCompraResponseDTO createPedidoCompra(SolicitacaoCertificadoMenssaging request) {
        log.debug("Criando pedido de compra para ID: {}", request.id());
        //var response = validApiClient.createPedidoCompra(
        //    new PedidoCompraRequestDTO(request.nome(), request.email(), request.id())
        //);
        
        return new PedidoCompraResponseDTO("1234567890");
    }

    private void updateStatusAndTicket(SolicitacaoCertificadoMenssaging request, 
                                     PedidoCompraResponseDTO response) {
        solicitacaoCertificadoService.addTicketNumber(request.id(), response.ticket());
        solicitacaoCertificadoService.updateStatus(
            request.id(),
            StatusSolicitacaoCertificado.PEDIDO_COMPRA_CONCLUIDO
        );
    }

    private void publishNextMessage(SolicitacaoCertificadoMenssaging request) {
        var nextMessage = new SolicitacaoBoletoMenssaging(
            request.nome(),
            request.email(),
            request.id()
        );
        
        solicitacaoBoletoProducer.publish(nextMessage);
    }
}