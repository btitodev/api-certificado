package com.api.certificado.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.certificado.menssaging.BoletoEmitidoMenssaging;

@Service
public class CsoApiClient {

    @Autowired
    private WebClient webClient;

    public void sendBoletoEmitido(BoletoEmitidoMenssaging message) {
        webClient.post()
                .uri("/api/cso/boleto-emitido")
                .bodyValue(message)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
