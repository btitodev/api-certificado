package com.api.certificado.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.AgendamentoRequestDTO;
import com.api.certificado.dto.AgendamentoResponseDTO;
import com.api.certificado.dto.PedidoCompraRequestDTO;
import com.api.certificado.dto.PedidoCompraResponseDTO;
import com.api.certificado.service.SolicitacaoCertificadoService;

@Service
public class ValidApiClient {

    @Autowired
    private WebClient webClient;

    @Autowired
    SolicitacaoCertificadoService solicitacaoCertificadoService;

    public PedidoCompraResponseDTO createPedidoCompra(PedidoCompraRequestDTO request) {
        try {
            var pedidoCompraResponse = webClient.post()
                    .uri("https://api.valid.com.br/api/pedidos")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PedidoCompraResponseDTO.class)
                    .block();

            return pedidoCompraResponse;
        } catch (Exception e) {
            solicitacaoCertificadoService.updateStatus(request.idSolicitacao(),
                    StatusSolicitacaoCertificado.FALHA_PEDIDO_COMPRA);
            return null;
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
            solicitacaoCertificadoService.updateStatus(agendamentoRequestDTO.idSolicitacao(),
                    StatusSolicitacaoCertificado.FALHA_AGENDAMENTO);
            return null;
        }

    }
}
