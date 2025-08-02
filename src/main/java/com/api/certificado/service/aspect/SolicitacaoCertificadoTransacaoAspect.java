package com.api.certificado.service.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SolicitacaoCertificadoTransacaoAspect {

    private final TransacaoAsyncService transacaoAsyncService;

    @AfterReturning(
        pointcut = "execution(* com.api.certificado.service.SolicitacaoCertificadoService.create(..))",
        returning = "solicitacaoId"
    )
    public void gerarTransacaoAposSolicitacao(JoinPoint joinPoint, UUID solicitacaoId) {
        log.info("*** ASPECT CREATE EXECUTADO - ID: {}", solicitacaoId);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    log.info("*** AFTER COMMIT CREATE - ID: {}", solicitacaoId);
                    transacaoAsyncService.createTransactionAsync(solicitacaoId);
                } catch (Exception e) {
                    log.error("Erro no callback de criação de transação para solicitação ID: {}", solicitacaoId, e);
                }
            }
        });
    }

    @AfterThrowing(
        pointcut = "execution(* com.api.certificado.service.SolicitacaoCertificadoService.create(..))",
        throwing = "exception"
    )
    public void gerarTransacaoAposErroSolicitacao(JoinPoint joinPoint, Exception exception) {
        log.error("*** ASPECT CREATE ERRO EXECUTADO - Erro: {}", exception.getMessage());
        try {
            transacaoAsyncService.createTransactionForErrorAsync(exception);
        } catch (Exception e) {
            log.error("Erro ao criar transação para erro de solicitação: {}", e.getMessage(), e);
        }
    }

    @AfterReturning(
        pointcut = "execution(* com.api.certificado.service.SolicitacaoCertificadoService.updateStatus(..)) && args(id, status)",
        argNames = "joinPoint,id,status"
    )
    public void gerarTransacaoAposUpdateStatus(JoinPoint joinPoint, UUID id, StatusSolicitacaoCertificado status) {
        log.info("*** ASPECT UPDATE STATUS EXECUTADO - ID: {}, Status: {}", id, status);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    log.info("*** AFTER COMMIT UPDATE STATUS - ID: {}, Status: {}", id, status);
                    transacaoAsyncService.createTransactionForStatusUpdateAsync(id, status);
                } catch (Exception e) {
                    log.error("Erro no callback de criação de transação para update de status. ID: {}, Status: {}", 
                             id, status, e);
                }
            }
        });
    }
}
