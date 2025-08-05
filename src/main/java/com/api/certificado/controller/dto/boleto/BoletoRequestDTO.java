package com.api.certificado.controller.dto.boleto;

import java.util.UUID;

public record BoletoRequestDTO(
    String nome, 
    String email,
     UUID idSolicitacao) {

}
