package com.api.certificado.controller.dto.solicitacaoCertificado;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;

public record SolicitacaoCertificadoResponseDTO(
        UUID id,
        LocalDateTime dataSolicitacao,
        StatusSolicitacaoCertificado status) {

}
