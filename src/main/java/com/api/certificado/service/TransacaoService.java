package com.api.certificado.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.api.certificado.domain.transacao.Transacao;
import com.api.certificado.dto.TransacaoRequestDTO;
import com.api.certificado.repository.TransacaoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;

    @Transactional
    public UUID create(TransacaoRequestDTO request) {

        try {
            var transacao = new Transacao(request);
            repository.save(transacao);
            repository.flush();
            return transacao.getId();
        } catch (Exception ex) {
            throw new RuntimeException("Falha ao salvar transação");
        }
    }

}
