package com.api.certificado.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;

public record TransacaoResponseDTO(
        UUID id,
        UUID solicitacaoCertificadoId,
        LocalDateTime data,
        StatusSolicitacaoCertificado status,
        Boolean sucesso,
        Boolean mensagem) {

}
