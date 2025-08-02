package com.api.certificado.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
    String error,
    String message,
    int status,
    String path,
    LocalDateTime timestamp,
    List<String> details
) {
    public ErrorResponseDTO(String error, String message, int status, String path) {
        this(error, message, status, path, LocalDateTime.now(), null);
    }
    
    public ErrorResponseDTO(String error, String message, int status, String path, List<String> details) {
        this(error, message, status, path, LocalDateTime.now(), details);
    }
}
