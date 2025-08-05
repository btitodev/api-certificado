package com.api.certificado.controller.dto.solicitacaoCertificado;


import jakarta.validation.constraints.NotNull;

public record SolicitacaoCertificadoRequestDTO(
    @NotNull(message = "Dados do requerente são obrigatórios")
    SolicitanteRequestDTO requerente,

    @NotNull(message = "Dados do cliente são obrigatórios")
    SolicitanteRequestDTO cliente
) {}
