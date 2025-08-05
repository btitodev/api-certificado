package com.api.certificado.util.mapper;

import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitanteRequestDTO;
import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitanteResponseDTO;
import com.api.certificado.domain.solicitacaoCertificado.Solicitante;

public class SolicitanteMapper {

    public static Solicitante toEntity(SolicitanteRequestDTO dto) {
        if (dto == null) return null;

        Solicitante entity = new Solicitante();
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setDocumento(dto.documento());
        entity.setTelefone(dto.telefone());

        return entity;
    }

    public static SolicitanteRequestDTO toRequestDto(Solicitante entity) {
        if (entity == null) return null;

        return new SolicitanteRequestDTO(
                entity.getNome(),
                entity.getEmail(),
                entity.getDocumento(),
                entity.getTelefone()
        );
    }

    public static SolicitanteResponseDTO toResponseDto(Solicitante entity) {
        if (entity == null) return null;

        return new SolicitanteResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getDocumento(),
                entity.getTelefone()
        );
    }

    public static Solicitante toEntity(SolicitanteResponseDTO dto) {
        if (dto == null) return null;

        Solicitante entity = new Solicitante();
        entity.setId(dto.id());
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setDocumento(dto.documento());
        entity.setTelefone(dto.telefone());

        return entity;
    }
}
