package com.api.certificado.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;

public record SolicitacaoCertificadoResponseDTO(
        UUID id,
        String nome,
        String email,
        LocalDateTime dataSolicitacao,
        StatusSolicitacaoCertificado status,
        String ticket) {

}
