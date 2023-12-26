package br.com.desafio.totvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Classe principal de inicialização da aplicação
 */
@SpringBootApplication
@ComponentScan(basePackages = "br.com.desafio.totvs")
public class Startup {

	/**
	 * Método main da aplicação
	 * @param args	Args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}

}
