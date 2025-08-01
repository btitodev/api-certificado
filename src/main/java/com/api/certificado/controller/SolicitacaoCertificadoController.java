package com.api.certificado.controller;

import java.net.URI;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.certificado.dto.SolicitacaoCertificadoRequestDTO;
import com.api.certificado.dto.SolicitacaoCertificadoResponseDTO;
import com.api.certificado.service.SolicitacaoCertificadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/solicitacao-certificado")
public class SolicitacaoCertificadoController {

    @Autowired
    private SolicitacaoCertificadoService service;

    @PostMapping
    public ResponseEntity<SolicitacaoCertificadoResponseDTO> create(
            @RequestBody @Valid SolicitacaoCertificadoRequestDTO request) throws Exception {

        var id = service.create(request);

        service.publishSolicitacao(id);

        //throw new Exception("teste");
       SolicitacaoCertificadoResponseDTO response = service.getById(id);

        URI location = URI.create("/solicitacao-certificado/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoCertificadoResponseDTO> getById(@PathVariable UUID id) {
        SolicitacaoCertificadoResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }


}
