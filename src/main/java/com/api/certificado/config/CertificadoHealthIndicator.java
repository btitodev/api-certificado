package com.api.certificado.config;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.api.certificado.repository.SolicitacaoCertificadoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("certificado") 
@RequiredArgsConstructor
public class CertificadoHealthIndicator implements HealthIndicator {
    
    private final SolicitacaoCertificadoRepository repository;
    private final RabbitTemplate rabbitTemplate;
    
    @Override
    public Health health() {
        LocalDateTime startTime = LocalDateTime.now();
        
        try {
            long dbResponseTime = checkDatabase();
            
            long rabbitResponseTime = checkRabbitMQ();
            
            Duration totalResponseTime = Duration.between(startTime, LocalDateTime.now());
            
            return Health.up()
                    .withDetail("database", "available")
                    .withDetail("database_response_time_ms", dbResponseTime)
                    .withDetail("rabbitmq", "available")
                    .withDetail("rabbitmq_response_time_ms", rabbitResponseTime)
                    .withDetail("total_check_time_ms", totalResponseTime.toMillis())
                    .withDetail("timestamp", LocalDateTime.now().toString())
                    .build();
                    
        } catch (Exception e) {
            log.error("Health check falhou", e);
            
            Duration totalResponseTime = Duration.between(startTime, LocalDateTime.now());
            
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("error_type", e.getClass().getSimpleName())
                    .withDetail("total_check_time_ms", totalResponseTime.toMillis())
                    .withDetail("timestamp", LocalDateTime.now().toString())
                    .build();
        }
    }
    
    private long checkDatabase() throws Exception {
        LocalDateTime start = LocalDateTime.now();
        
        try {
            repository.count();
            return Duration.between(start, LocalDateTime.now()).toMillis();
        } catch (Exception e) {
            log.error("Database health check falhou", e);
            throw new Exception("Database unavailable: " + e.getMessage(), e);
        }
    }
    
    private long checkRabbitMQ() throws Exception {
        LocalDateTime start = LocalDateTime.now();
        
        try {
            var connection = rabbitTemplate.getConnectionFactory().createConnection();
            connection.close();
            return Duration.between(start, LocalDateTime.now()).toMillis();
        } catch (Exception e) {
            log.error("RabbitMQ health check falhou", e);
            throw new Exception("RabbitMQ unavailable: " + e.getMessage(), e);
        }
    }
}
