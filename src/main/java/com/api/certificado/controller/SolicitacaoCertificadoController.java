package com.api.certificado.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.certificado.annotation.RequireApiKey;
import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitacaoCertificadoResponseDTO;
import com.api.certificado.service.SolicitacaoCertificadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/solicitacao-certificado")
public class SolicitacaoCertificadoController {

    private final SolicitacaoCertificadoService service;

    public SolicitacaoCertificadoController(SolicitacaoCertificadoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SolicitacaoCertificadoResponseDTO> create(
            @RequestBody @Valid SolicitacaoCertificadoRequestDTO request) {
        var response = service.create(request);
        service.sendMessagingSolicitacaoBoleto(response);
        URI location = URI.create("/solicitacao-certificado/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @RequireApiKey
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoCertificadoResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

}
