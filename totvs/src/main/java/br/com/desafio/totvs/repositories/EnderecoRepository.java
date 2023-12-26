package br.com.desafio.totvs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.totvs.model.Cliente;
import br.com.desafio.totvs.model.Endereco;
import java.util.List;


/**
 * Repositório do Endereço que extende JpaRepository
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    /**
     * Método de busca de endereços por cliente
     * @param cliente   Busca endereços por cliente
     * @return          Retorna lista de clientes
     */
    List<Endereco> findByCliente(Cliente cliente);
}
