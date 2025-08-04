package com.api.certificado.controller.dto.agendamento;

import java.util.UUID;

public record AgendamentoRequestDTO(
        String nome,
        String email,
        UUID idSolicitacao) {
}
