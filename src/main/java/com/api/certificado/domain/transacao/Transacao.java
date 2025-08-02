package com.api.certificado.domain.transacao;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;
import com.api.certificado.dto.TransacaoRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    
    private UUID solicitacaoCertificadoId;
    private LocalDateTime data;
    private StatusSolicitacaoCertificado status;
    private Boolean sucesso;
    private Boolean mensagem;

    public Transacao (TransacaoRequestDTO request){
        this.solicitacaoCertificadoId = request.solicitacaoCertificadoId();
        this.data = LocalDateTime.now();
        this.status = request.status();
        this.sucesso = request.sucesso();
        this.mensagem = request.mensagem();
    }
}
