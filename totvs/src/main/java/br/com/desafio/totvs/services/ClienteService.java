package br.com.desafio.totvs.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.desafio.totvs.data.vo.v1.ClienteVO;
import br.com.desafio.totvs.exceptions.ResourceBadRequestException;
import br.com.desafio.totvs.exceptions.ResourceNotFoundException;
import br.com.desafio.totvs.mapper.DozerMapper;
import br.com.desafio.totvs.model.Cliente;
import br.com.desafio.totvs.repositories.ClienteRepository;
import br.com.desafio.totvs.utils.ValidarNulo;

/**
 * Classe do serviço com métodos de regra de negócio para
 * cliente
 * 
 * @author  Wallison Junior Cardoso Soares Silva
 */
@Service
public class ClienteService {

    /**
     * Instanciação do logger para cliente service
     */
    private Logger logger = Logger.getLogger(ClienteService.class.getName());

    /**
     * Chamada a classe repository do cliente
     */
    private ClienteRepository _repository;

    /**
     * Instanciação do repository cliente
     * @param repository    Repository cliente
     * @author              Wallison Junior Cardoso Soares Silva
     */
    public ClienteService(ClienteRepository repository) {
        this._repository = repository;
    }

    /**
     * Método responsável por buscar todos clientes cadastrados
     * 
     * @return      Retorna uma lista de ClienteVO
     * @author      Wallison Junior Cardoso Soares Silva
     */
    public List<ClienteVO> findAll() {
        logger.info("Listando todos clientes");

        List<Cliente> clientes = _repository.findAll();

        if (clientes.size() > 0)
            return DozerMapper.parseListObject(clientes, ClienteVO.class);
        else
            return new ArrayList<ClienteVO>();
    }

    /**
     * Método responsável por buscar um cliente cadastrado por Id
     * 
     * @param id    Id do cliente buscado
     * @return      Retorna um ClienteVO
     * @author      Wallison Junior Cardoso Soares Silva
     */
    public ClienteVO findById(Long id) {
        logger.info("Encontrando cliente pelo Id");

        var entity = _repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhuma informação encontrada!"));

        return DozerMapper.parseObject(entity, ClienteVO.class);
    }

    /**
     * Método responsável por criar um cliente, contém validação
     * se nome de cliente é maior que 10 caracteres e se o nome já
     * possui cadastro para algum cliente no sistema
     * 
     * @param cliente     Objeto com dados do cliente para inserção
     * @return              Retorna um ClienteVO
     * @author              Wallison Junior Cardoso Soares Silva
     */
    public ClienteVO create(ClienteVO cliente) {
        logger.info("Criando novo cliente!");

        var entity = DozerMapper.parseObject(cliente, Cliente.class);

        var verificarCliente = _repository.findByNome(entity.getNome());
        
        if (cliente.getNome().length() < 11)
            throw new ResourceBadRequestException("Nome cliente no mínimo 11 caracteres!");
        if (verificarCliente != null)
            throw new ResourceBadRequestException("Cliente já cadastrado!");

        var vo = DozerMapper.parseObject(_repository.save(entity), ClienteVO.class);

        return vo;
    }

    /**
     * Método responsável por atualizar um cliente, contém validação
     * se nome de cliente é maior que 10 caracteres e se o nome já
     * possui cadastro para algum cliente no sistema
     * 
     * @param cliente     Objeto com dados do cliente para atualização
     * @return              Retorna um ClienteVO
     * @author              Wallison Junior Cardoso Soares Silva
     */
    public ClienteVO update(ClienteVO cliente) {
        logger.info("Atualizando um cliente!");

        var entity = _repository.findById(cliente.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));

        var verificarCliente = _repository.findByNome(cliente.getNome());

        if (verificarCliente != null)
            throw new ResourceBadRequestException("Cliente já cadastrado!");
        else if (cliente.getNome().length() < 10)
            throw new ResourceBadRequestException("Nome cliente no mínimo 11 caracteres!");

        var validarNulo = new ValidarNulo();

        entity.setNome(validarNulo.naoNulo(cliente.getNome(), entity.getNome()));
        entity.setSexo(validarNulo.naoNulo(cliente.getSexo(), entity.getSexo()));
        entity.setDataNascimento(validarNulo.naoNulo(cliente.getDataNascimento(), entity.getDataNascimento()));
        
        var vo = DozerMapper.parseObject(_repository.save(entity), ClienteVO.class);

        return vo;
    }

    /**
     * Método responsável por deletar um cliente cadastrado
     * 
     * @param id    Id do cliente que deseja ser deletado
     * @author      Wallison Junior Cardoso Soares Silva
     */
    public void delete(Long id) {
        logger.info("Deletando cliente!");
        
        var entity = _repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado!"));
        
        _repository.delete(entity);
    }
    
}
