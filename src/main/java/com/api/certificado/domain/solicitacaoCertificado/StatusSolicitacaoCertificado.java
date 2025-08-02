package com.api.certificado.domain.solicitacaoCertificado;

public enum StatusSolicitacaoCertificado {
    SOLICITACAO_EMITIDA("Solicitação emitida"),
    BOLETO_SOLICITADO("Boleto solicitado à R-Payment"),
    BOLETO_EMITIDO("Boleto emitido"),
    BOLETO_ENVIADO("Boleto enviado"),
    AGUARDANDO_PAGAMENTO("Aguardando pagamento do boleto"),
    BOLETO_PAGO("Boleto pago"),
    PEDIDO_COMPRA_SOLICITADO("Pedido de compra enviado para Valid"),
    PEDIDO_COMPRA_CONCLUIDO("Pedido de compra concluído"),
    AGENDAMENTO_SOLICITADO("Agendamento solicitado à Valid"),
    AGENDADO("Agendamento concluído"),
    CANCELADA("Solicitação cancelada"),
    FALHA_PEDIDO_COMPRA("Falha ao solicitar pedido de compra"), 
    FALHA_AGENDAMENTO("Falha ao solicitar agendamento"),
    FALHA_ENVIO_BOLETO("Falha ao enviar boleto"),
    FALHA_SOLICITACAO("Falha ao processar solicitação");

    private final String descricao;

    StatusSolicitacaoCertificado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
