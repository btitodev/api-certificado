package com.api.certificado.menssaging.message;

import java.util.UUID;

import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitanteResponseDTO;

public record SolicitacaoBoletoMenssaging(
        UUID idSolicitacao,
        SolicitanteResponseDTO cliente
) {
}
