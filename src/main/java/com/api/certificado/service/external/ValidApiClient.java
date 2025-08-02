package com.api.certificado.service.external;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.certificado.dto.AgendamentoRequestDTO;
import com.api.certificado.dto.AgendamentoResponseDTO;
import com.api.certificado.dto.PedidoCompraRequestDTO;
import com.api.certificado.dto.PedidoCompraResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidApiClient {

    private final WebClient webClient;

    public PedidoCompraResponseDTO createPedidoCompra(PedidoCompraRequestDTO request) {
        try {
            return webClient.post()
                    .uri("https://api.valid.com.br/api/pedidos")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PedidoCompraResponseDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar pedido de compra", e);
        }
    }

    public AgendamentoResponseDTO createAgendamento(AgendamentoRequestDTO agendamentoRequestDTO) {
        try {
            var agendamentoResponse = webClient.post()
                    .uri("https://api.valid.com.br/api/agendamentos")
                    .bodyValue(agendamentoRequestDTO)
                    .retrieve()
                    .bodyToMono(AgendamentoResponseDTO.class)
                    .block();

            return agendamentoResponse;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao solicitar agendamento", e);
        }

    }
}
