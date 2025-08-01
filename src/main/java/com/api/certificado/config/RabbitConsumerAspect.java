package com.api.certificado.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RabbitConsumerAspect {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConsumerAspect.class);

    @Around("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    public Object interceptRabbitListener(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            Object[] args = joinPoint.getArgs();
            String payload = args.length > 0 ? args[0].toString() : "N/A";

            String errorMessage = String.format(
                "Falha ao processar mensagem na fila %s. Erro: %s. Payload: %s",
                joinPoint.getSignature().toShortString(), // Nome do método listener
                ex.getMessage(),
                payload
            );
            
            logger.error(errorMessage, ex);

            // Rejeita a mensagem e a envia para a fila DLQ
            throw new AmqpRejectAndDontRequeueException(errorMessage, ex);
        }
    }
}