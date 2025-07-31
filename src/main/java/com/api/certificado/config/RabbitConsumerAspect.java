package com.api.certificado.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RabbitConsumerAspect {

    @Around("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    public Object interceptRabbitListener(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            // Log estruturado pode ir aqui
            System.err.println("Erro interceptado no consumer: " + ex.getMessage());

            // Enviar para sistema de métricas, se quiser
            // metricService.increment("consumer.falha");

            throw new AmqpRejectAndDontRequeueException("Erro tratado pelo Aspect", ex);
        }
    }
}
