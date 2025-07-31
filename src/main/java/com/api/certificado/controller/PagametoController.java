package com.api.certificado.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.certificado.service.PagamentoService;


@RestController
@RequestMapping("/api/pagamento")
public class PagametoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PutMapping("/{idSolicitacao}")
    public ResponseEntity<Void> confirmarPagamento(@PathVariable UUID idSolicitacao) {
        pagamentoService.confirmarPagamento(idSolicitacao);
        return ResponseEntity.ok().build();
    }

}
