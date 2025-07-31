package com.api.certificado.menssaging;

import java.util.UUID;

public record SolicitacaoAgendamentoMenssaging(
    String nome,
    String email,
    UUID idSolicitacao
) {
}
