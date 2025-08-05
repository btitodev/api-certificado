package com.api.certificado.controller.dto.boleto;

import java.util.UUID;

public record BoletoResponseDTO(
    String nome, 
    String email, 
    UUID idSolicitacao) {
}
