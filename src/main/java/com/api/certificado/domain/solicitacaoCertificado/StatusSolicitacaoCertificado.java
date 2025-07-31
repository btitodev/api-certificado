package com.api.certificado.domain.solicitacaoCertificado;

public enum StatusSolicitacaoCertificado {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    REJEITADO("Rejeitado"),
    PROCESSANDO("Processando"),
    FALHA("Falha"),
    PEDIDO_COMPRA_SOLICITADO("Pedido de Compra Solicitado"),
    BOLETO_EMITIDO("Boleto Emitido"),
    BOLETO_SOLICITADO("Boleto Solicitado"),
    BOLETO_PAGO("Boleto Pago");

    private final String descricao;

    StatusSolicitacaoCertificado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
