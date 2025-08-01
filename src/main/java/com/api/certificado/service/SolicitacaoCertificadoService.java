package com.api.certificado.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.certificado.domain.MessagePublisher;
import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.dto.SolicitacaoCertificadoResponseDTO;
import com.api.certificado.menssaging.SolicitacaoCertificadoMenssaging;
import com.api.certificado.repository.SolicitacaoCertificadoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SolicitacaoCertificadoService {

        @Autowired
        private SolicitacaoCertificadoRepository repository;

        @Autowired
        private MessagePublisher<SolicitacaoCertificadoMenssaging> producerCertificado;

        @Autowired
        private EntityManager entityManager;

        @Transactional
        public UUID create(SolicitacaoCertificadoRequestDTO request) {
                var solicitacao = new SolicitacaoCertificado(request);
                repository.save(solicitacao);
                entityManager.flush(); 
                return solicitacao.getId();
        }

        public void publishSolicitacao(UUID id) {
                var solicitacao = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada: " + id));

                SolicitacaoCertificadoMenssaging mensagem = new SolicitacaoCertificadoMenssaging(
                                solicitacao.getId(),
                                solicitacao.getNome(),
                                solicitacao.getEmail());

                producerCertificado.publish(mensagem);
        }

        public void updateStatus(UUID id, StatusSolicitacaoCertificado status) {
                var solicitacao = repository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada"));

                solicitacao.setStatus(status);
                repository.save(solicitacao);
        }

        public void addTicketNumber(UUID id, String ticket) {
                var solicitacao = repository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada"));

                solicitacao.setTicket(ticket);
                repository.save(solicitacao);
        }

        public SolicitacaoCertificadoResponseDTO getById(UUID id) {
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
