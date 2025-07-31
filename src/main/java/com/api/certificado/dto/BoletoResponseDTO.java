package com.api.certificado.dto;

import java.util.UUID;

public record BoletoResponseDTO(
    String nome, 
    String email, 
    UUID idSolicitacao) {
}
