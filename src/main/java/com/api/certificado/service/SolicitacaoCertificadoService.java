package com.api.certificado.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;
import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificadoResponseDTO;
import com.api.certificado.repository.SolicitacaoCertificadoRepository;

@Service
public class SolicitacaoCertificadoService {

    @Autowired
    private SolicitacaoCertificadoRepository repository;

    public SolicitacaoCertificadoResponseDTO create(SolicitacaoCertificadoRequestDTO request) {
        var newSolicitacaoCertificado = new SolicitacaoCertificado(request);

        repository.save(newSolicitacaoCertificado);

        return new SolicitacaoCertificadoResponseDTO(
                newSolicitacaoCertificado.getId(),
                newSolicitacaoCertificado.getNome(),
                newSolicitacaoCertificado.getEmail(),
                newSolicitacaoCertificado.getDataSolicitacao(),
                newSolicitacaoCertificado.getStatus());
    }

}
