package br.com.desafio.totvs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.totvs.model.Cliente;

/**
 * Repositório do Cliente que extende JpaRepository
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    /**
     * Método para encontrar cliente por nome
     * @param nome  Nome do cliente para buscar
     * @return      Retorna Cliente encontrado
     * @author      Wallison Junior Cardoso Soares Silva
     */
    Cliente findByNome(String nome);
}
