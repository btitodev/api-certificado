package com.api.certificado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.api.certificado.controller.SolicitacaoCertificadoController;

@SpringBootApplication
public class CertificadoApplication {

	private static final Logger logger = LoggerFactory.getLogger(SolicitacaoCertificadoController.class);

	public static void main(String[] args) {
		System.out.println("Iniciando aplicação Certificado");
		logger.info("Iniciando aplicação Certificado");

		try {
			SpringApplication.run(CertificadoApplication.class, args);
			System.out.println("Aplicação iniciada com sucesso ");
		} catch (Exception e) {
			System.out.println("Erro ao iniciar a aplicação");
			e.printStackTrace();
		}

	}
}
