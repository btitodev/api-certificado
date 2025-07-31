package com.api.certificado.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.certificado.dto.BoletoRequestDTO;
import com.api.certificado.dto.BoletoResponseDTO;

@Service
public class RPaymentApiClient {

    @Autowired
    private WebClient webClient;

    public BoletoResponseDTO createBoleto(BoletoRequestDTO request) {
        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Mock response for demonstration purposes
        var mockResponse = new BoletoResponseDTO(request.nome(), request.email(), request.idSolicitacao());
        return mockResponse;

        // Uncomment below to use actual API call
        // var boletoResponse = webClient.post()
        //         .uri("https://api.rpayment.com.br/api/boletos")
        //         .bodyValue(request)
        //         .retrieve()
        //         .bodyToMono(BoletoResponse.class)
        //         .block();

        // return boletoResponse;
    }
}
