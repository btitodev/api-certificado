package com.api.certificado.dto;

import java.util.UUID;

public record SolicitacaoCertificadoRequestDTO(
        String nome,
        String email,
        UUID idSolicitacao) {
}
