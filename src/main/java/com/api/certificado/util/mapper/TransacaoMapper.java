package com.api.certificado.util.mapper;

import java.time.LocalDateTime;

import com.api.certificado.controller.dto.transacao.TransacaoRequestDTO;
import com.api.certificado.domain.transacao.Transacao;
import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;

public class TransacaoMapper {
    
    public static Transacao toEntity(TransacaoRequestDTO dto) {
        if (dto == null) return null;

        Transacao entity = new Transacao();
        entity.setData(LocalDateTime.now());
        entity.setStatus(dto.status());
        entity.setSucesso(dto.sucesso());
        entity.setMensagem(dto.mensagem());
        
        if (dto.solicitacaoCertificadoId() != null) {
            SolicitacaoCertificado solicitacao = new SolicitacaoCertificado();
            solicitacao.setId(dto.solicitacaoCertificadoId());
            entity.setSolicitacaoCertificado(solicitacao);
        }

        return entity;
    }
}
