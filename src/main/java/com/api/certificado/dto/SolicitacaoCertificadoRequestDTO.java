package com.api.certificado.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SolicitacaoCertificadoRequestDTO(
        
        @NotNull(message = "Nome não pode ser nulo")
        @NotBlank(message = "Nome não pode estar vazio")
        @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
        String nome,
        
        @NotNull(message = "Email não pode ser nulo")
        @NotBlank(message = "Email não pode estar vazio")
        @Email(message = "Email deve ter um formato válido")
        @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
        String email
) {}
