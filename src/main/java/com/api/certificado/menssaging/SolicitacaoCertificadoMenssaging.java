package com.api.certificado.menssaging;

import java.util.UUID;

public record SolicitacaoCertificadoMenssaging(UUID id, String nome, String email) {

    public SolicitacaoCertificadoMenssaging {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
    }

}
