package br.com.desafio.totvs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.totvs.model.Cliente;
import br.com.desafio.totvs.model.Telefone;
import java.util.List;


/**
 * Repositório do Telefone que extende JpaRepository
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
    /**
     * Método para encontrar telefone pelo numero do contato
     * @param contato   Numero do contato para ser buscado
     * @return          Retorna telefone encontrado
     */
    Telefone findByContato(String contato);

    /**
     * Método para encontrar telefones pelo cliente
     * @param cliente   Cliente para ser buscado
     * @return          Retorna telefones encontrado
     */
    List<Telefone> findByCliente(Cliente cliente);
}
