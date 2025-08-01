package com.api.certificado.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.certificado.domain.MessagePublisher;
import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.dto.SolicitacaoCertificadoResponseDTO;
import com.api.certificado.menssaging.SolicitacaoBoletoMenssaging;
import com.api.certificado.repository.SolicitacaoCertificadoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SolicitacaoCertificadoService {

        @Autowired
        private SolicitacaoCertificadoRepository repository;

        @Autowired
        private MessagePublisher<SolicitacaoBoletoMenssaging> sendMessagingBoleto;

        @Autowired
        private EntityManager entityManager;

        @Transactional
        public UUID create(SolicitacaoCertificadoRequestDTO request) {
                try {
                        var solicitacao = new SolicitacaoCertificado(request);
                        repository.save(solicitacao);
                        entityManager.flush();
                        return solicitacao.getId();
                } catch (Exception ex) {
                        throw new RuntimeException("Falha ao salvar a solicitação", ex);
                }
        }

        public void sendMessagingSolicitacaoBoleto(SolicitacaoCertificadoRequestDTO request) {

                var menssagingSolicitacaoBoleto = new SolicitacaoBoletoMenssaging(
                                request.nome(),
                                request.email(),
                                request.idSolicitacao());

                sendMessagingBoleto.publish(menssagingSolicitacaoBoleto);
                updateStatus(request.idSolicitacao(),
                                StatusSolicitacaoCertificado.BOLETO_SOLICITADO);
        }

        @Transactional
        public void updateStatus(UUID id, StatusSolicitacaoCertificado status) {
                try {
                        var solicitacao = repository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada"));
                        solicitacao.setStatus(status);
                        repository.save(solicitacao);
                } catch (Exception ex) {
                        throw new RuntimeException("Erro ao atualizar o status da solicitação", ex);
                }
        }

        @Transactional
        public void addTicketNumber(UUID id, String ticket) {
                try {
                        var solicitacao = repository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada"));
                        solicitacao.setTicket(ticket);
                        repository.save(solicitacao);
                } catch (Exception ex) {
                        throw new RuntimeException("Erro ao adicionar número do ticket", ex);
                }
        }

        public SolicitacaoCertificadoResponseDTO getById(UUID id) {
                try {
                        var solicitacao = repository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada"));

                        return new SolicitacaoCertificadoResponseDTO(
                                        solicitacao.getId(),
                                        solicitacao.getNome(),
                                        solicitacao.getEmail(),
                                        solicitacao.getDataSolicitacao(),
                                        solicitacao.getStatus());
                } catch (Exception ex) {
                        throw new RuntimeException("Erro ao buscar solicitação por ID", ex);
                }
        }

}
