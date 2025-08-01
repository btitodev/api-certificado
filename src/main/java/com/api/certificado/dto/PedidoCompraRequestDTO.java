package com.api.certificado.dto;

import java.util.UUID;

public record PedidoCompraRequestDTO(
    String nome, 
    String email,
    UUID idSolicitacao) {

}
