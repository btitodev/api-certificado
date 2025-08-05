package com.api.certificado.controller.dto.agendamento;

import java.util.UUID;

public record AgendamentoResponseDTO(
        String nome,
        String email,
        UUID idSolicitacao) {
}
