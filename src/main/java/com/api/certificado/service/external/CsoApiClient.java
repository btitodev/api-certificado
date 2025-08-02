package com.api.certificado.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.menssaging.BoletoEmitidoMenssaging;
import com.api.certificado.service.SolicitacaoCertificadoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CsoApiClient {

    private final WebClient webClient;
    private final SolicitacaoCertificadoService solicitacaoCertificadoService;

    public void sendBoletoEmitido(BoletoEmitidoMenssaging message) {
        try {
            System.out.println("Enviando mensagem de boleto emitido: " + message);

            webClient.post()
                    .uri("/api/cso/boleto-emitido")
                    .bodyValue(message)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe();

        } catch (Exception e) {
            solicitacaoCertificadoService.updateStatus(message.idSolicitacao(),
                    StatusSolicitacaoCertificado.FALHA_ENVIO_BOLETO);
        }

    }
}
