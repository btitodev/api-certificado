package com.api.certificado.service;

import org.springframework.stereotype.Service;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.menssaging.message.BoletoMenssaging;
import com.api.certificado.service.external.CsoApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoletoProcessorService {

    private final CsoApiClient csoApiClient;
    private final SolicitacaoCertificadoService solicitacaoCertificadoService;

    public void processarBoletoEmitido(BoletoMenssaging message) {
        try {
            log.info("Iniciando processamento de boleto emitido para ID: {}", message.idSolicitacao());
            
            // Atualizar status no banco
            solicitacaoCertificadoService.updateStatus(
                message.idSolicitacao(), 
                StatusSolicitacaoCertificado.BOLETO_EMITIDO
            );
            
            // Adicionar link do boleto
            solicitacaoCertificadoService.addBoletoLink(
                message.idSolicitacao(), 
                message.link()
            );
            
            // Enviar para CSO se necessário
            // csoApiClient.sendBoletoEmitido(message);
            
            log.info("Boleto emitido processado com sucesso para ID: {}", message.idSolicitacao());
            
        } catch (Exception e) {
            log.error("Erro ao processar boleto emitido para ID: {}", message.idSolicitacao(), e);
            throw e;
        }
    }

    public void processarBoletoPago(BoletoMenssaging message) {
        try {
            log.info("Iniciando processamento de boleto pago para ID: {}", message.idSolicitacao());
            
            // Atualizar status no banco
            solicitacaoCertificadoService.updateStatus(
                message.idSolicitacao(), 
                StatusSolicitacaoCertificado.BOLETO_PAGO
            );
            
            // Processar pagamento - criar pedido de compra
            // csoApiClient.createPedidoCompra(message);
            
            log.info("Boleto pago processado com sucesso para ID: {}", message.idSolicitacao());
            
        } catch (Exception e) {
            log.error("Erro ao processar boleto pago para ID: {}", message.idSolicitacao(), e);
            throw e;
        }
    }

    public void processarBoletoAguardandoPagamento(BoletoMenssaging message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processarBoletoAguardandoPagamento'");
    }

    public void processarBoletoCancelado(BoletoMenssaging message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processarBoletoCancelado'");
    }
}
