package com.api.certificado.domain.solicitacaoCertificado;

public interface MessagePublisher<T> {
    void publish(T message);
}
