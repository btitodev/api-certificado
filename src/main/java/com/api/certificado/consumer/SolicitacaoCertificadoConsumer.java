package com.api.certificado.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.menssaging.SolicitacaoCertificadoMenssaging;
import com.api.certificado.service.SolicitacaoCertificadoService;

@Component
public class SolicitacaoCertificadoConsumer {

    
    @Autowired
    private SolicitacaoCertificadoService solicitacaoCertificadoService;

    @RabbitListener(queues = "${broker.queue.solicitacao.name}")
    public void receiveMessage(SolicitacaoCertificadoMenssaging request) {
        solicitacaoCertificadoService.updateStatus(request.id(), StatusSolicitacaoCertificado.PROCESSANDO);
    }

}
