package com.api.certificado.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;

@Service
public class PagamentoService {

    @Autowired
    private SolicitacaoCertificadoService solicitacaoCertificadoService;

    public void confirmarPagamento(UUID idSolicitacao) {
        // Atualiza o status da solicitação de certificado para "Boleto Pago"

        System.out.println("Confirmando pagamento para a solicitação: " + idSolicitacao);
        solicitacaoCertificadoService.updateStatus(idSolicitacao, StatusSolicitacaoCertificado.BOLETO_PAGO);
        System.out.println("Pagamento confirmado para a solicitação: " + idSolicitacao);
    }

}
