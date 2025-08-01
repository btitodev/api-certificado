package com.api.certificado.menssaging;

import java.util.UUID;

public record SolicitacaoBoletoMenssaging(
    String nome, 
    String email, 
    UUID idSolicitacao) {
}
    