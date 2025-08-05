package com.api.certificado.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitacaoCertificadoResponseDTO;
import com.api.certificado.domain.MessagePublisher;
import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;
import com.api.certificado.domain.solicitacaoCertificado.Solicitante;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.exception.SolicitacaoNaoEncontradaException;
import com.api.certificado.menssaging.message.SolicitacaoBoletoMenssaging;
import com.api.certificado.repository.SolicitacaoCertificadoRepository;
import com.api.certificado.util.mapper.SolicitacaoCertificadoMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitacaoCertificadoService implements ApplicationContextAware {

        private final SolicitacaoCertificadoRepository repository;
        private final SolicitanteService solicitanteService;
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
        public SolicitacaoCertificadoResponseDTO create(SolicitacaoCertificadoRequestDTO request) {
                try {
                        var requerente = solicitanteService.searchOrCreate(request.requerente());

                        var cliente = requerente;

                        if (!solicitanteService.areTheSamePerosn(request.requerente(), request.cliente())) {
                                cliente = solicitanteService.searchOrCreate(request.cliente());
                        }

                        var solicitacao = createSolicitacao(requerente, cliente);

                        repository.saveAndFlush(solicitacao);

                         return SolicitacaoCertificadoMapper.toResponseDto(solicitacao);

                } catch (Exception ex) {
                        throw new RuntimeException("Falha ao salvar a solicitação", ex);
                }
        }

        public void sendMessagingSolicitacaoBoleto(SolicitacaoCertificadoResponseDTO request) {
                var menssagingSolicitacaoBoleto = new SolicitacaoBoletoMenssaging(
                                request.id(),
                                request.cliente(),
                                request.status());

                sendMessagingBoleto.publish(menssagingSolicitacaoBoleto);
                getSelf().updateStatus(request.id(), StatusSolicitacaoCertificado.BOLETO_SOLICITADO);
        }

        private SolicitacaoCertificado createSolicitacao(Solicitante requerente, Solicitante cliente) {

                var solicitacao = new SolicitacaoCertificado();

                solicitacao.setDataSolicitacao(LocalDateTime.now());
                solicitacao.setStatus(StatusSolicitacaoCertificado.SOLICITACAO_EMITIDA);
                solicitacao.setRequerente(requerente); 
                solicitacao.setCliente(cliente);       

                return solicitacao;
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
                                        .orElseThrow(() -> new SolicitacaoNaoEncontradaException(
                                                        "Solicitação não encontrada"));

                        return SolicitacaoCertificadoMapper.toResponseDto(solicitacao);
                } catch (SolicitacaoNaoEncontradaException ex) {
                        throw ex;
                } catch (Exception ex) {
                        throw new RuntimeException("Erro ao buscar solicitação por ID", ex);
                }
        }
}
