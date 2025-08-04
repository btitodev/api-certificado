package com.api.certificado.menssaging.message;

import java.util.UUID;

public record BoletoEmitidoMenssaging(
        String nome,
        String email,
        String boletoUrl,
        UUID idSolicitacao) {
}
