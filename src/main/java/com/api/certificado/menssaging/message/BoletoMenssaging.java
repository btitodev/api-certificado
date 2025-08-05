package com.api.certificado.menssaging.message;

import java.math.BigDecimal;
import java.util.UUID;

public record BoletoMenssaging(
        UUID idSolicitacao,
        String cobrancaNumero,
        BigDecimal valorTotal,
        String status,
        String vencimento,
        String pagamento,
        String link) {
}
