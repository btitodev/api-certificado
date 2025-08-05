package com.api.certificado.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.certificado.domain.solicitacaoCertificado.Solicitante;

public interface SolicitanteRepository extends JpaRepository<Solicitante, UUID> {

    boolean existsByDocumento(String documento);

    Optional<Solicitante> findByDocumento(String documento);
}
