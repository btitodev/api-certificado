package com.api.certificado.service.aspect;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.api.certificado.controller.dto.transacao.TransacaoRequestDTO;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.service.TransacaoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoAsyncService {

    private final TransacaoService transacaoService;

    @Async
    public void createTransactionAsync(UUID solicitacaoId) {
        try {
            log.info("*** INICIANDO ASYNC CREATE - Solicitação ID: {}", solicitacaoId);
            
            var transacaoRequest = new TransacaoRequestDTO(
                    solicitacaoId,
                    LocalDateTime.now(),
                    StatusSolicitacaoCertificado.SOLICITACAO_EMITIDA,
                    true,
                    "Solicitação emitida com sucesso"
            );
            
            transacaoService.create(transacaoRequest);
            
            log.info("*** SUCESSO ASYNC CREATE - Solicitação ID: {}", solicitacaoId);
        } catch (Exception e) {
            log.error("*** ERRO ASYNC CREATE - Solicitação ID: {}", solicitacaoId, e);
        }
    }

    @Async
    public void createTransactionForStatusUpdateAsync(UUID solicitacaoId, StatusSolicitacaoCertificado status) {
        try {
            log.info("*** INICIANDO ASYNC UPDATE STATUS - Solicitação ID: {}, Status: {}", 
                    solicitacaoId, status);
            
            var transacaoRequest = new TransacaoRequestDTO(
                    solicitacaoId,
                    LocalDateTime.now(),
                    status,
                    true,
                    "Status atualizado para " + status.name()
            );
            
            transacaoService.create(transacaoRequest);
            
            log.info("*** SUCESSO ASYNC UPDATE STATUS - Solicitação ID: {}, Status: {}", 
                    solicitacaoId, status);
        } catch (Exception e) {
            log.error("*** ERRO ASYNC UPDATE STATUS - Solicitação ID: {}, Status: {}", 
                     solicitacaoId, status, e);
        }
    }

    @Async
    public void createTransactionForErrorAsync(Exception error) {
        try {
            log.info("*** INICIANDO ASYNC ERROR TRANSACTION - Erro: {}", error.getMessage());
            
            var transacaoRequest = new TransacaoRequestDTO(
                    null, 
                    LocalDateTime.now(),
                    StatusSolicitacaoCertificado.FALHA_SOLICITACAO, 
                    false, 
                    error.getMessage() 
            );
            
            transacaoService.createErrorTransaction(transacaoRequest, error.getMessage());
            
            log.info("*** SUCESSO ASYNC ERROR TRANSACTION - Erro: {}", error.getMessage());
        } catch (Exception e) {
            log.error("*** ERRO ASYNC ERROR TRANSACTION - Erro original: {}, Erro ao salvar: {}", 
                     error.getMessage(), e.getMessage(), e);
        }
    }
}