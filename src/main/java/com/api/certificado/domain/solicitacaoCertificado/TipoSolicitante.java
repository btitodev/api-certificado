package com.api.certificado.domain.solicitacaoCertificado;

public enum TipoSolicitante {
    REQUERENTE("Requerente"),
    CLIENTE("Cliente");

    private final String descricao;

    TipoSolicitante(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
