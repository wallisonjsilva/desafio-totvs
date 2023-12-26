package br.com.desafio.totvs.repositories;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import br.com.desafio.totvs.model.Cliente;
import br.com.desafio.totvs.repositories.interfaces.IClienteRepository;

@Repository
public class ClienteRepository extends SimpleJpaRepository<Cliente, Long> implements IClienteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ClienteRepository(EntityManager entityManager) {
        super(Cliente.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Cliente findByNome(String nome) {
        String query = "FROM Cliente c WHERE c.nome = :nome";
        return entityManager.createQuery(query, Cliente.class).setParameter("nome", nome).getSingleResult();
    }
}
