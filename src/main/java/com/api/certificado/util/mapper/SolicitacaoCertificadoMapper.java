package com.api.certificado.util.mapper;

import java.time.LocalDateTime;

import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;

public class SolicitacaoCertificadoMapper {

    public static SolicitacaoCertificado toEntity(SolicitacaoCertificadoRequestDTO dto) {
        if (dto == null) return null;

        SolicitacaoCertificado entity = new SolicitacaoCertificado();
        entity.setDataSolicitacao(LocalDateTime.now()); 
        entity.setStatus(StatusSolicitacaoCertificado.SOLICITACAO_EMITIDA); 

        entity.setRequerente(SolicitanteMapper.toEntity(dto.requerente()));
        entity.setCliente(SolicitanteMapper.toEntity(dto.cliente()));

        return entity;
    }
}
