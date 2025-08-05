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

    
    @Transactional
    public Solicitante searchOrCreate(SolicitanteRequestDTO dto) {
        Optional<Solicitante> solicitanteExistente = repository.findByDocumento(dto.documento());

        if (solicitanteExistente.isPresent()) {
            var solicitante = solicitanteExistente.get();
            
            update(solicitante, dto);
            
            return repository.saveAndFlush(solicitante);
        }
        
        var novoSolicitante = createSolicitante(dto);
        
        return repository.saveAndFlush(novoSolicitante);
    }

    /**
     * Atualiza dados do solicitante se houver mudanças
     */
    private void update(Solicitante solicitante, SolicitanteRequestDTO dto) {
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
            solicitante.setTelefone(dto.telefone());
            foiAtualizado = true;
        }
    }

    /**
     * Cria um novo solicitante
     */
    private Solicitante createSolicitante(SolicitanteRequestDTO dto) {
        var solicitante = new Solicitante();
        solicitante.setNome(dto.nome());
        solicitante.setEmail(dto.email());
        solicitante.setTelefone(dto.telefone());
        solicitante.setDocumento(dto.documento());
        
        return solicitante;
    }

    /**
     * Verifica se requerente e cliente são a mesma pessoa
     */
    public boolean areTheSamePerosn(SolicitanteRequestDTO requerente, SolicitanteRequestDTO cliente) {
        return requerente.documento().equals(cliente.documento());
    }
}
