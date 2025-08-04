package com.api.certificado.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitanteRequestDTO;
import com.api.certificado.domain.solicitacaoCertificado.Solicitante;
import com.api.certificado.repository.SolicitanteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitanteService {

    private final SolicitanteRepository repository;

    /**
     * Busca ou cria um solicitante baseado no CPF
     */
    @Transactional
    public Solicitante buscarOuCriarSolicitante(SolicitanteRequestDTO dto) {
        log.info("Buscando ou criando solicitante com CPF: {}", dto.documento());
        
        // 1. Tentar encontrar por CPF primeiro
        Optional<Solicitante> solicitanteExistente = repository.findByDocumento(dto.documento());

        if (solicitanteExistente.isPresent()) {
            var solicitante = solicitanteExistente.get();
            log.info("Solicitante encontrado: {}", solicitante.getId());
            
            // 2. Atualizar dados se necessário
            atualizarDadosSeNecessario(solicitante, dto);
            
            return repository.saveAndFlush(solicitante);
        }
        
        // 3. Criar novo solicitante se não existe
        log.info("Criando novo solicitante para CPF: {}", dto.documento());
        var novoSolicitante = criarNovoSolicitante(dto);
        
        return repository.saveAndFlush(novoSolicitante);
    }

    /**
     * Atualiza dados do solicitante se houver mudanças
     */
    private void atualizarDadosSeNecessario(Solicitante solicitante, SolicitanteRequestDTO dto) {
        boolean foiAtualizado = false;
        
        // Atualizar nome se diferente
        if (!dto.nome().equals(solicitante.getNome())) {
            log.info("Atualizando nome do solicitante {} de '{}' para '{}'", 
                    solicitante.getId(), solicitante.getNome(), dto.nome());
            solicitante.setNome(dto.nome());
            foiAtualizado = true;
        }
        
        // Atualizar email se diferente
        if (!dto.email().equals(solicitante.getEmail())) {
            log.info("Atualizando email do solicitante {} de '{}' para '{}'", 
                    solicitante.getId(), solicitante.getEmail(), dto.email());
            solicitante.setEmail(dto.email());
            foiAtualizado = true;
        }
        
        // Atualizar telefone se fornecido e diferente
        if (dto.telefone() != null && !dto.telefone().equals(solicitante.getTelefone())) {
            log.info("Atualizando telefone do solicitante {}", solicitante.getId());
            solicitante.setTelefone(dto.telefone());
            foiAtualizado = true;
        }
        
        
        if (foiAtualizado) {
            log.info("Dados do solicitante {} foram atualizados", solicitante.getId());
        }
    }

    /**
     * Cria um novo solicitante
     */
    private Solicitante criarNovoSolicitante(SolicitanteRequestDTO dto) {
        var solicitante = new Solicitante();
        solicitante.setNome(dto.nome());
        solicitante.setEmail(dto.email());
        solicitante.setTelefone(dto.telefone());
        
        log.info("Novo solicitante criado com CPF: {}", dto.documento());
        return solicitante;
    }

    /**
     * Verifica se requerente e cliente são a mesma pessoa
     */
    public boolean saoAMesmaPessoa(SolicitanteRequestDTO requerente, SolicitanteRequestDTO cliente) {
        return requerente.documento().equals(cliente.documento());
    }
}
