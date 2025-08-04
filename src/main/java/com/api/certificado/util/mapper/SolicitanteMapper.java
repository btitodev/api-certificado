package com.api.certificado.util.mapper;

import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitanteRequestDTO;
import com.api.certificado.domain.solicitacaoCertificado.Solicitante;

public class SolicitanteMapper {

    public static Solicitante toEntity(SolicitanteRequestDTO dto) {
        if (dto == null) return null;

        Solicitante entity = new Solicitante();
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setDocumento(dto.documento());
        entity.setTelefone(dto.telefone());
        entity.setTipo(dto.tipo());

        return entity;
    }
}
