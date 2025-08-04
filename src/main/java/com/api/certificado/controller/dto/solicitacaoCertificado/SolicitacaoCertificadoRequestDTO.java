package com.api.certificado.controller.dto.solicitacaoCertificado;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record SolicitacaoCertificadoRequestDTO(
    @NotNull(message = "Dados do requerente são obrigatórios")
    @JsonProperty("applicant")
    SolicitanteRequestDTO requerente,

    @NotNull(message = "Dados do cliente são obrigatórios")
    @JsonProperty("customer")
    SolicitanteRequestDTO cliente
) {}
