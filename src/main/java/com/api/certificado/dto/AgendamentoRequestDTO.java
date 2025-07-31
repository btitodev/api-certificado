package com.api.certificado.dto;

import java.util.UUID;

public record AgendamentoRequestDTO(
        String nome,
        String email,
        UUID idSolicitacao) {
}
