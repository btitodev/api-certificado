package com.api.certificado.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.api.certificado.controller.dto.transacao.TransacaoRequestDTO;
import com.api.certificado.domain.transacao.Transacao;
import com.api.certificado.repository.TransacaoRepository;
import com.api.certificado.util.mapper.TransacaoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UUID create(TransacaoRequestDTO request) {
        try {
            Transacao novaTransacao = TransacaoMapper.toEntity(request);

            Transacao transacaoSalva = transacaoRepository.save(novaTransacao);
            return transacaoSalva.getId();

        } catch (Exception ex) {
            log.error("Erro ao salvar transação padrão", ex);
            throw new RuntimeException("Falha ao salvar transação padrão", ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UUID createErrorTransaction(TransacaoRequestDTO request, String errorMessage) {
        try {
            Transacao transacaoErro = new Transacao();
            transacaoErro.setData(request.data());
            transacaoErro.setStatus(request.status());
            transacaoErro.setSucesso(false);
            transacaoErro.setMensagem(errorMessage);

            Transacao transacaoSalva = transacaoRepository.save(transacaoErro);

            log.warn("Transação de erro criada: {} - Motivo: {}", transacaoSalva.getId(), errorMessage);
            return transacaoSalva.getId();

        } catch (Exception ex) {
            log.error("Erro ao criar transação de erro: {}", errorMessage, ex);
            throw new RuntimeException("Falha ao salvar transação de erro", ex);
        }
    }
}
