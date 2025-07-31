package com.api.certificado.domain;

public interface MessagePublisher<T> {
    void publish(T message);
}
