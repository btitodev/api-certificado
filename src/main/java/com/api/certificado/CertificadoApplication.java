package com.api.certificado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CertificadoApplication {

	public static void main(String[] args) {
		System.out.println("Iniciando aplicação Certificado");

		try {
			SpringApplication.run(CertificadoApplication.class, args);
			System.out.println("Aplicação iniciada com sucesso ");
		} catch (Exception e) {
			System.out.println("Erro ao iniciar a aplicação");
			e.printStackTrace();
		}

	}
}
