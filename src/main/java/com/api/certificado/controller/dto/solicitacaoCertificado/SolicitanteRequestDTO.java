package com.api.certificado.controller.dto.solicitacaoCertificado;


public record SolicitanteRequestDTO(
        String nome,
        String email,
        String documento,
        String telefone
) {}
