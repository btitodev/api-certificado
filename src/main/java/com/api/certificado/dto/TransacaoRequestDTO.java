package com.api.certificado.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;

public record TransacaoRequestDTO(
        UUID solicitacaoCertificadoId,
        LocalDateTime data,
        StatusSolicitacaoCertificado status,
        Boolean sucesso,
        String mensagem) {
}
