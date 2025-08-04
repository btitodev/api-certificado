package com.api.certificado.controller.dto.solicitacaoCertificado;

import com.api.certificado.domain.solicitacaoCertificado.TipoSolicitante;

public record SolicitanteRequestDTO(
        String nome,
        String email,
        String documento,
        String telefone,
        TipoSolicitante tipo
) {}
