package com.api.certificado.dto;

import java.util.UUID;

public record AgendamentoResponseDTO(
        String nome,
        String email,
        UUID idSolicitacao) {
}
