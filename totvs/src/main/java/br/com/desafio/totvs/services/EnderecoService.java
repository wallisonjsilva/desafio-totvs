package br.com.desafio.totvs.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.totvs.data.vo.v1.EnderecoVO;
import br.com.desafio.totvs.exceptions.ResourceBadRequestException;
import br.com.desafio.totvs.exceptions.ResourceNotFoundException;
import br.com.desafio.totvs.mapper.DozerMapper;
import br.com.desafio.totvs.model.Cliente;
import br.com.desafio.totvs.model.Endereco;
import br.com.desafio.totvs.repositories.ClienteRepository;
import br.com.desafio.totvs.repositories.EnderecoRepository;
import br.com.desafio.totvs.utils.ValidarNulo;

/**
 * Classe do serviço com métodos de regra de negócio para
 * endereço
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
@Service
public class EnderecoService {
    /**
     * Instancia do logger da classe endereço service
     */
    private Logger logger = Logger.getLogger(EnderecoService.class.getName());

    /**
     * Instancia do endereço repository
     */
    @Autowired
    private EnderecoRepository _repository;

    /**
     * Instancia do cliente repository
     */
    @Autowired
    private ClienteRepository _clienteRepository;

    /**
     * Método responsável por buscar todos endereços cadastrados
     * 
     * @return Retorna uma lista de EnderecoVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public List<EnderecoVO> findAll() {
        logger.info("Listando todos enderecos");

        List<Endereco> enderecos = _repository.findAll();

        if (enderecos.size() > 0)
            return DozerMapper.parseListObject(enderecos, EnderecoVO.class);
        else
            return new ArrayList<EnderecoVO>();
    }

    /**
     * Método responsável por buscar um endereço por Id
     * 
     * @param id Id do endereço buscado
     * @return Retorna um objeto do tipo EnderecoVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public EnderecoVO findById(Long id) {
        logger.info("Encontrando endereco pelo Id");

        var entity = _repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma informação encontrada!"));

        return DozerMapper.parseObject(entity, EnderecoVO.class);
    }

    /**
     * Método responsável por buscar endereços por ClienteId
     * 
     * @param clienteId        Cliente Id para buscar
     * @return          Retorna um objeto do tipo EnderecoVO
     * @author          Wallison Junior Cardoso Soares Silva
     */
    public List<EnderecoVO> findByClienteId(Long clienteId) {
        logger.info("Encontrando endereco pelo Id");

        var clienteEntity = _clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma informação encontrada!"));

        var entity = _repository.findByCliente(DozerMapper.parseObject(clienteEntity, Cliente.class));

        return DozerMapper.parseListObject(entity, EnderecoVO.class);
    }

    /**
     * Método responsável por inserir um endereço para um cliente, contém validação
     * se cliente passado não é nulo
     * 
     * @param endereco Objeto do Endereco que deseja ser inserido
     * @return Retorna um Objeto do tipo EnderecoVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public EnderecoVO create(EnderecoVO endereco) {
        logger.info("Criando novo endereco!");

        if (endereco.getCliente() == null)
            throw new ResourceBadRequestException("Cliente não pode ser nulo!");

        try {
            var entity = DozerMapper.parseObject(endereco, Endereco.class);

            var vo = DozerMapper.parseObject(_repository.save(entity), EnderecoVO.class);

            return vo;
        } catch (Exception ex) {
            throw new ResourceBadRequestException("Endereço incompleto!");
        }

    }

    /**
     * Método responsável por atualizar um endereço para um cliente, contém
     * validação
     * se cliente passado não é nulo
     * 
     * @param endereco Objeto do Endereco que deseja ser atualizado
     * @return Retorna um Objeto do tipo EnderecoVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public EnderecoVO update(EnderecoVO endereco) {
        logger.info("Atualizando um endereco!");

        if (endereco.getCliente() == null)
            throw new ResourceBadRequestException("Cliente não pode ser nulo!");

        var entity = _repository.findById(endereco.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));

        var validarNulo = new ValidarNulo();

        var cliente = DozerMapper.parseObject(endereco.getCliente(), Cliente.class);

        entity.setBairro(validarNulo.naoNulo(endereco.getBairro(), entity.getBairro()));
        entity.setCep(validarNulo.naoNulo(endereco.getCep(), entity.getCep()));
        entity.setCidade(validarNulo.naoNulo(endereco.getCidade(), entity.getCidade()));
        entity.setCliente(cliente);
        entity.setEstado(validarNulo.naoNulo(endereco.getEstado(), entity.getEstado()));
        entity.setLogradouro(validarNulo.naoNulo(endereco.getLogradouro(), entity.getLogradouro()));
        entity.setNumero(validarNulo.naoNulo(endereco.getNumero(), entity.getNumero()));
        entity.setPais(validarNulo.naoNulo(endereco.getPais(), entity.getPais()));

        var vo = DozerMapper.parseObject(_repository.save(entity), EnderecoVO.class);

        return vo;
    }

    /**
     * Método responsável por deletar um endereço cadastrado
     * 
     * @param id Id do endereço que deseja ser deletado
     * @author Wallison Junior Cardoso Soares Silva
     */
    public void delete(Long id) {
        logger.info("Deletando endereco!");

        var entity = _repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado!"));

        _repository.delete(entity);
    }
}
