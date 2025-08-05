package com.api.certificado.menssaging.message;

import java.util.UUID;

import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitanteResponseDTO;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;

public record SolicitacaoBoletoMenssaging(
        UUID idSolicitacao,
        SolicitanteResponseDTO requerente,
        SolicitanteResponseDTO cliente,
        StatusSolicitacaoCertificado status
) {
}
