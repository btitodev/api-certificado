package com.api.certificado.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.api.certificado.controller.dto.ErrorResponseDTO;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SolicitacaoNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> handleSolicitacaoNaoEncontrada(
            SolicitacaoNaoEncontradaException ex, WebRequest request) {
        
        log.warn("Solicitação não encontrada: {}", ex.getMessage());
        
        var errorResponse = new ErrorResponseDTO(
            "SOLICITACAO_NAO_ENCONTRADA",
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFound(
            EntityNotFoundException ex, WebRequest request) {
        
        log.warn("Entidade não encontrada: {}", ex.getMessage());
        
        var errorResponse = new ErrorResponseDTO(
            "ENTIDADE_NAO_ENCONTRADA",
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SolicitacaoInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleSolicitacaoInvalida(
            SolicitacaoInvalidaException ex, WebRequest request) {
        
        log.warn("Solicitação inválida: {}", ex.getMessage());
        
        var errorResponse = new ErrorResponseDTO(
            "SOLICITACAO_INVALIDA",
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        List<String> details = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            details.add(error.getField() + ": " + error.getDefaultMessage());
        }
        
        log.warn("Erro de validação: {}", details);
        
        var errorResponse = new ErrorResponseDTO(
            "ERRO_VALIDACAO",
            "Dados de entrada inválidos",
            HttpStatus.BAD_REQUEST.value(),
            request.getDescription(false).replace("uri=", ""),
            details
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }    

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        
        log.error("Erro interno do servidor: {}", ex.getMessage(), ex);
        
        var errorResponse = new ErrorResponseDTO(
            "ERRO_INTERNO",
            "Ocorreu um erro interno no servidor",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex, WebRequest request) {
        
        log.error("Erro inesperado: {}", ex.getMessage(), ex);
        
        var errorResponse = new ErrorResponseDTO(
            "ERRO_INESPERADO",
            "Ocorreu um erro inesperado",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
