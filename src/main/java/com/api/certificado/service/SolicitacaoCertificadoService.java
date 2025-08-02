package com.api.certificado.service;

import java.util.UUID;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.certificado.domain.MessagePublisher;
import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.dto.SolicitacaoCertificadoResponseDTO;
import com.api.certificado.exception.SolicitacaoNaoEncontradaException;
import com.api.certificado.menssaging.SolicitacaoBoletoMenssaging;
import com.api.certificado.repository.SolicitacaoCertificadoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitacaoCertificadoService implements ApplicationContextAware {

        private final SolicitacaoCertificadoRepository repository;
        private final MessagePublisher<SolicitacaoBoletoMenssaging> sendMessagingBoleto;

        private ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
                this.applicationContext = applicationContext;
        }

        private SolicitacaoCertificadoService getSelf() {
                return applicationContext.getBean(SolicitacaoCertificadoService.class);
        }

        @Transactional
        public UUID create(SolicitacaoCertificadoRequestDTO request) {
                try {
                        var solicitacao = new SolicitacaoCertificado(request);
                        repository.saveAndFlush(solicitacao);
                        return solicitacao.getId();
                } catch (Exception ex) {
                        throw new RuntimeException("Falha ao salvar a solicitação", ex);
                }
        }

        public void sendMessagingSolicitacaoBoleto(SolicitacaoCertificadoRequestDTO request, UUID id) {
                var menssagingSolicitacaoBoleto = new SolicitacaoBoletoMenssaging(
                                request.nome(),
                                request.email(),
                                request.idSolicitacao());

                sendMessagingBoleto.publish(menssagingSolicitacaoBoleto);
                getSelf().updateStatus(id, StatusSolicitacaoCertificado.BOLETO_SOLICITADO);
        }

        @Transactional
        public void updateStatus(UUID id, StatusSolicitacaoCertificado status) {
                try {
                        var solicitacao = repository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada"));
                        solicitacao.setStatus(status);
                        repository.saveAndFlush(solicitacao);
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
                        repository.saveAndFlush(solicitacao);
                } catch (Exception ex) {
                        throw new RuntimeException("Erro ao adicionar número do ticket", ex);
                }
        }

        public SolicitacaoCertificadoResponseDTO getById(UUID id) {
                try {
                        var solicitacao = repository.findById(id)
                                        .orElseThrow(() -> new SolicitacaoNaoEncontradaException("Solicitação não encontrada"));

                        return new SolicitacaoCertificadoResponseDTO(
                                        solicitacao.getId(),
                                        solicitacao.getNome(),
                                        solicitacao.getEmail(),
                                        solicitacao.getDataSolicitacao(),
                                        solicitacao.getStatus());
                } catch (SolicitacaoNaoEncontradaException ex) {
                        throw ex;
                } catch (Exception ex) {
                        throw new RuntimeException("Erro ao buscar solicitação por ID", ex);
                }
        }
}
