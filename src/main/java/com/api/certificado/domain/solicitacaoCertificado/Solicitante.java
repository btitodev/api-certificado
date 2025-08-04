package com.api.certificado.domain.solicitacaoCertificado;

import java.util.UUID;

import com.api.certificado.controller.dto.solicitacaoCertificado.SolicitanteRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "solicitante")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Solicitante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private String email;
    private String documento;
    private String telefone;
    private TipoSolicitante tipo;

    public Solicitante(SolicitanteRequestDTO request) {
        this.nome = request.nome();
        this.email = request.email();
        this.documento = request.documento();
        this.telefone = request.telefone();
        this.tipo = request.tipo();
    }
    
}
