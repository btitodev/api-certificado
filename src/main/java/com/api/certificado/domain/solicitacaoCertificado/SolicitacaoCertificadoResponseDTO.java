package com.api.certificado.domain.solicitacaoCertificado;

import java.time.LocalDateTime;
import java.util.UUID;

public record SolicitacaoCertificadoResponseDTO(UUID id, String nome, String email, LocalDateTime dataSolicitacao) {

}
