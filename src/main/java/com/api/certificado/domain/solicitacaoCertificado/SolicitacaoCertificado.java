package com.api.certificado.domain.solicitacaoCertificado;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.api.certificado.domain.transacao.Transacao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private LocalDateTime dataSolicitacao;
    private String ticket;
    private Integer numeroBoleto;
    private String linkBoleto;

    @ManyToOne
    @JoinColumn(name = "requerente_id")
    private Solicitante requerente;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Solicitante cliente;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacaoCertificado status;

    @OneToMany(mappedBy = "solicitacaoCertificado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();

}
