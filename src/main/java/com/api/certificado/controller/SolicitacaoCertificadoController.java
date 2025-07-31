package com.api.certificado.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificadoResponseDTO;
import com.api.certificado.service.SolicitacaoCertificadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/solicitacao-certificado")
public class SolicitacaoCertificadoController {

    @Autowired
    private SolicitacaoCertificadoService service;

    @PostMapping
    public ResponseEntity<SolicitacaoCertificadoResponseDTO> create(
            @RequestBody @Valid SolicitacaoCertificadoRequestDTO request) {
        SolicitacaoCertificadoResponseDTO response = service.create(request);

        URI location = URI.create("/solicitacao-certificado/" + response.id());

        return ResponseEntity.created(location).body(response);
    }

}
