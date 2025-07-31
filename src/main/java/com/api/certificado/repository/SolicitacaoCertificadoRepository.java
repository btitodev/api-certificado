package com.api.certificado.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.certificado.domain.solicitacaoCertificado.SolicitacaoCertificado;
import com.api.certificado.domain.solicitacaoCertificado.StatusSolicitacaoCertificado;

public interface SolicitacaoCertificadoRepository extends JpaRepository<SolicitacaoCertificado, UUID> {

    @Query("SELECT s FROM SolicitacaoCertificado s WHERE s.status = :status")
    List<SolicitacaoCertificado> findByStatus(StatusSolicitacaoCertificado status);

}
