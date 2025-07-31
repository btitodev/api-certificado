package com.api.certificado.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.certificado.dto.PedidoCompraRequestDTO;
import com.api.certificado.dto.PedidoCompraResponseDTO;

@Service
public class ValidApiClient {

    @Autowired
    private WebClient webClient;

    public PedidoCompraResponseDTO createPedidoCompra(PedidoCompraRequestDTO request) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        var mockResponse = new PedidoCompraResponseDTO("mock-ticket-12345");
        return mockResponse;

        // var pedidoCompraResponse = webClient.post()
        //         .uri("https://api.valid.com.br/api/pedidos") 
        //         .bodyValue(request)
        //         .retrieve()
        //         .bodyToMono(PedidoCompraResponseDTO.class)
        //         .block();

        // return pedidoCompraResponse;
    }
}
