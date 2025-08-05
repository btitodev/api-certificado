package com.api.certificado.menssaging.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.api.certificado.menssaging.message.BoletoMenssaging;
import com.api.certificado.service.BoletoProcessorService;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoletoConsumer {

    private final BoletoProcessorService boletoProcessorService;

    @Async
    @RabbitListener(queues = "${broker.queue.boleto.name}")
    public void receiveMessage(BoletoMenssaging message) {
        try {
            log.info("Recebida mensagem de boleto - ID: {}, Status: {}", 
                    message.idSolicitacao(), message.status());
            
            switch (message.status()) {
                case "paid", "settled" -> {
                    log.info("Processando boleto pago para ID: {}", message.idSolicitacao());
                    boletoProcessorService.processarBoletoPago(message);
                }
                case "waiting", "new" -> {
                    log.info("Processando boleto aguardando pagamento para ID: {}", message.idSolicitacao());
                    boletoProcessorService.processarBoletoAguardandoPagamento(message);
                }
                case "canceled" -> {
                    log.info("Processando boleto cancelado para ID: {}", message.idSolicitacao());
                    boletoProcessorService.processarBoletoCancelado(message);
                }
                default -> {
                    log.warn("Status de boleto não reconhecido: {} para ID: {}", 
                            message.status(), message.idSolicitacao());
                }
            }
            
        } catch (Exception ex) {
            log.error("Falha ao processar mensagem de boleto para ID: {}", 
                     message.idSolicitacao(), ex);
            throw ex; // Exception será tratada pelo RabbitMQ
        }
    }
}
