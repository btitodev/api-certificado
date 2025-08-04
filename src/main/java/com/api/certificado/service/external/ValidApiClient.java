package com.api.certificado.service.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.certificado.controller.dto.agendamento.AgendamentoRequestDTO;
import com.api.certificado.controller.dto.agendamento.AgendamentoResponseDTO;
import com.api.certificado.controller.dto.pedidoCompra.PedidoCompraRequestDTO;
import com.api.certificado.controller.dto.pedidoCompra.PedidoCompraResponseDTO;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.service.SolicitacaoCertificadoService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ValidApiClient {

    private static final Logger log = LoggerFactory.getLogger(ValidApiClient.class);

    private final WebClient webClient;
    private final SolicitacaoCertificadoService solicitacaoCertificadoService;

   public Mono<PedidoCompraResponseDTO> createPedidoCompra(PedidoCompraRequestDTO request) {
    return webClient.post()
            .uri("https://api.valid.com.br/api/pedidos")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(PedidoCompraResponseDTO.class)
            .retry(2)
            .doOnError(error -> log.error("Falha na criação de pedido após tentativas", error))
            .onErrorResume(error -> {
                solicitacaoCertificadoService.updateStatus(
                    request.idSolicitacao(),
                    StatusSolicitacaoCertificado.FALHA_PEDIDO_COMPRA
                );
                return Mono.error(new RuntimeException("Falha ao criar pedido de compra", error));
            });
}


    public Mono<AgendamentoResponseDTO> createAgendamento(AgendamentoRequestDTO agendamentoRequestDTO) {
        return webClient.post()
                .uri("https://api.valid.com.br/api/agendamentos")
                .bodyValue(agendamentoRequestDTO)
                .retrieve()
                .bodyToMono(AgendamentoResponseDTO.class)
                .retry(2)
                .doOnError(error -> {
                    solicitacaoCertificadoService.updateStatus(
                            agendamentoRequestDTO.idSolicitacao(),
                            StatusSolicitacaoCertificado.FALHA_AGENDAMENTO);
                })
                .onErrorResume(error -> {
                    return Mono.error(new RuntimeException("Falha ao solicitar agendamento", error));
                });
    }

}
