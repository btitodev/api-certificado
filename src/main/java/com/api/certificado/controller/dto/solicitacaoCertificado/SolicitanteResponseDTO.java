package com.api.certificado.controller.dto.solicitacaoCertificado;

import java.util.UUID;

public record SolicitanteResponseDTO(
        UUID id,
        String nome,
        String email,
        String documento,
        String telefone) {

}
