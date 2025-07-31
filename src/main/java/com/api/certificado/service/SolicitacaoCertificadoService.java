package com.api.certificado.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.dto.SolicitacaoCertificadoResponseDTO;
import com.api.certificado.menssaging.SolicitacaoCertificadoMenssaging;
import com.api.certificado.producer.SolicitacaoCertificadoProducer;
import com.api.certificado.repository.SolicitacaoCertificadoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SolicitacaoCertificadoService {

    @Autowired
    private SolicitacaoCertificadoRepository repository;

    @Autowired
    private SolicitacaoCertificadoProducer producer;

    @Transactional
    public SolicitacaoCertificadoResponseDTO create(SolicitacaoCertificadoRequestDTO request) {
        var newSolicitacaoCertificado = new SolicitacaoCertificado(request);

        repository.save(newSolicitacaoCertificado);

        SolicitacaoCertificadoMenssaging requestMessaging = new SolicitacaoCertificadoMenssaging(
                newSolicitacaoCertificado.getId(),
                newSolicitacaoCertificado.getNome(),
                newSolicitacaoCertificado.getEmail());

        producer.publishMessageSolicitacaocertificado(requestMessaging);

        return new SolicitacaoCertificadoResponseDTO(
                newSolicitacaoCertificado.getId(),
                newSolicitacaoCertificado.getNome(),
                newSolicitacaoCertificado.getEmail(),
                newSolicitacaoCertificado.getDataSolicitacao(),
                newSolicitacaoCertificado.getStatus(),
                newSolicitacaoCertificado.getTicket());
    }

    public void updateStatus(UUID id, StatusSolicitacaoCertificado status) {
        var solicitacao = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada"));

        solicitacao.setStatus(status);
        repository.save(solicitacao);
    }

    public  SolicitacaoCertificadoResponseDTO getById(UUID id) {
        var solicitacao = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada"));

        return new SolicitacaoCertificadoResponseDTO(
                solicitacao.getId(),
                solicitacao.getNome(),
                solicitacao.getEmail(),
                solicitacao.getDataSolicitacao(),
                solicitacao.getStatus(),
                solicitacao.getTicket());
    }

}
