package com.api.certificado.domain.transacao;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.TransacaoRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "transacao")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_certificado_id", nullable = true)
    private SolicitacaoCertificado solicitacaoCertificado;

    private LocalDateTime data;
    private Boolean sucesso;
    private String mensagem;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacaoCertificado status;

    public Transacao(SolicitacaoCertificado solicitacaoCertificado, StatusSolicitacaoCertificado status, Boolean sucesso, String mensagem) {
        this.solicitacaoCertificado = solicitacaoCertificado;
        this.data = LocalDateTime.now();
        this.status = status;
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

    public Transacao(StatusSolicitacaoCertificado status, Boolean sucesso, String mensagem) {
        this.solicitacaoCertificado = null;
        this.data = LocalDateTime.now();
        this.status = status;
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

    public Transacao(TransacaoRequestDTO request) {
        this.data = LocalDateTime.now();
        this.status = request.status();
        this.sucesso = request.sucesso();
        this.mensagem = request.mensagem();
    }
}
