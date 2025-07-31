package com.api.certificado.dto;

import java.util.UUID;

public record BoletoRequestDTO(
    String nome, 
    String email,
     UUID idSolicitacao) {

}
