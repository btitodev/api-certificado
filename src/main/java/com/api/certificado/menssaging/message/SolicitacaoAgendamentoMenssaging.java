package com.api.certificado.menssaging.message;

import java.util.UUID;

public record SolicitacaoAgendamentoMenssaging(
        String nome,
        String email,
        UUID idSolicitacao) {
}
