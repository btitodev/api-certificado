package com.api.certificado.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.certificado.menssaging.BoletoEmitidoMenssaging;
import com.api.certificado.service.external.CsoApiClient;

@Component
public class BoletoEmitidoConsumer {

    @Autowired
    private CsoApiClient csoApiClient;  

    @RabbitListener(queues = "${broker.queue.boleto.emitido.name}")
    public void receiveMessage(BoletoEmitidoMenssaging message) {
        System.out.println("Processando mensagem de boleto emitido: " + message);

        //csoApiClient.sendBoletoEmitido(message);

        System.out.println("Boleto enviado com sucesso para a solicitação: " + message.idSolicitacao());
    }
}
