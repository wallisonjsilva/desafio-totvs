package br.com.desafio.totvs;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.desafio.totvs.controllers.ClienteController;
import br.com.desafio.totvs.repositories.ClienteRepository;
import br.com.desafio.totvs.services.ClienteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class AppConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public ClienteRepository clienteRepository() {
        return new ClienteRepository(entityManager);
    }
    
    @Bean
    public ClienteService clienteService(ClienteRepository clienteRepository) {
        return new ClienteService(clienteRepository);
    }

    @Bean
    public ClienteController clienteController(ClienteService clienteService) {
        return new ClienteController(clienteService);
    }
}
