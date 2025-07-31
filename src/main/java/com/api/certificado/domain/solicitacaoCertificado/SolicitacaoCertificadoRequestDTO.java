package com.api.certificado.domain.solicitacaoCertificado;

public record SolicitacaoCertificadoRequestDTO(String nome, String email) {

    public SolicitacaoCertificadoRequestDTO {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
    }


}
