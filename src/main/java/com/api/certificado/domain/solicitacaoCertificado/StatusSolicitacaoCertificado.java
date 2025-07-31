package com.api.certificado.domain.solicitacaoCertificado;

public enum StatusSolicitacaoCertificado {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    REJEITADO("Rejeitado");

    private final String descricao;

    StatusSolicitacaoCertificado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
