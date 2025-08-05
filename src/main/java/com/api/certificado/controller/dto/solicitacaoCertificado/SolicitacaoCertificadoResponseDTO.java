package com.api.certificado.controller.dto.solicitacaoCertificado;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;

public record SolicitacaoCertificadoResponseDTO(
        UUID id,
        StatusSolicitacaoCertificado status,
        LocalDateTime dataSolicitacao,
        SolicitanteResponseDTO requerente,
        SolicitanteResponseDTO cliente,
        String linkBoleto) {

}
