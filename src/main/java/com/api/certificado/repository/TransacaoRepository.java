package com.api.certificado.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.certificado.domain.transacao.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {}
