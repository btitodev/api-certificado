package com.api.certificado.domain.solicitacaoCertificado;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.certificado.dto.SolicitacaoCertificadoRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "solicitacao_certificado")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoCertificado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String email;
    private LocalDateTime dataSolicitacao;
    private String ticket;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacaoCertificado status;

    public SolicitacaoCertificado(SolicitacaoCertificadoRequestDTO request) {
        this.nome = request.nome();
        this.email = request.email();
        this.dataSolicitacao = LocalDateTime.now();     
        this.status = StatusSolicitacaoCertificado.SOLICITACAO_EMITIDA;
    }

}
