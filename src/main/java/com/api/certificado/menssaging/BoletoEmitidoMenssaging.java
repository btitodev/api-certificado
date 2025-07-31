package com.api.certificado.menssaging;

import java.util.UUID;

public record BoletoEmitidoMenssaging (String nome, String email, String boletoUrl, UUID idSolicitacao) {
}
