package com.api.certificado.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "api.security")
public class ApiSecurityProperties {
    private boolean enabled = true;
    private String headerName = "X-API-Key";
    private List<String> validKeys;
}
