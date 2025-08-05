package com.api.certificado.controller.dto.pedidoCompra;

import java.util.UUID;

public record PedidoCompraRequestDTO(
    String nome, 
    String email,
    UUID idSolicitacao) {

}
