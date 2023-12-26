package br.com.desafio.totvs.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.totvs.data.vo.v1.TelefoneVO;
import br.com.desafio.totvs.exceptions.ResourceBadRequestException;
import br.com.desafio.totvs.exceptions.ResourceNotFoundException;
import br.com.desafio.totvs.mapper.DozerMapper;
import br.com.desafio.totvs.model.Cliente;
import br.com.desafio.totvs.model.Telefone;
import br.com.desafio.totvs.repositories.ClienteRepository;
import br.com.desafio.totvs.repositories.TelefoneRepository;
import br.com.desafio.totvs.utils.ValidarNulo;

/**
 * Classe do serviço com métodos de regra de negócio para
 * telefone
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
@Service
public class TelefoneService {
    /**
     * Instancia do logger pra classe telefone service
     */
    private Logger logger = Logger.getLogger(TelefoneService.class.getName());

    /**
     * Instancia da classe telefone repository
     */
    @Autowired
    private TelefoneRepository _repository;

    /**
     * Instancia da classe cliente repository
     */
    @Autowired
    private ClienteRepository _clienteRepository;

    /**
     * Método responsável por buscar todos telefones cadastrados
     * 
     * @return Retorna uma lista de TelefoneVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public List<TelefoneVO> findAll() {
        logger.info("Listando todos clientes");

        List<Telefone> telefones = _repository.findAll();

        if (telefones.size() > 0)
            return DozerMapper.parseListObject(telefones, TelefoneVO.class);
        else
            return new ArrayList<TelefoneVO>();
    }

    /**
     * Método responsável por buscar um telefones por cliente Id
     * 
     * @param clienteId Id do cliente que deve ser buscado
     * @return Retorna um objeto do tipo TelefoneVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public List<TelefoneVO> findByClienteId(Long clienteId) {
        logger.info("Encontrando telefone pelo Id");

        var clienteEntity = _clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma informação encontrada!"));

        var entity = _repository.findByCliente(DozerMapper.parseObject(clienteEntity, Cliente.class));

        return DozerMapper.parseListObject(entity, TelefoneVO.class);
    }

    /**
     * Método responsável por buscar um telefone por Id
     * 
     * @param id Id do telefone buscado
     * @return Retorna um objeto do tipo TelefoneVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public TelefoneVO findById(Long id) {
        logger.info("Encontrando telefone pelo Id");

        var entity = _repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma informação encontrada!"));

        return DozerMapper.parseObject(entity, TelefoneVO.class);
    }

    /**
     * Método responsável por buscar um telefone por número do contato, contém
     * validação
     * se entidade buscada não é nulo
     * 
     * @param contato Objeto do Telefone que deseja ser inserido
     * @return Retorna um Objeto do tipo TelefoneVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public TelefoneVO findByTelefone(String contato) {
        logger.info("Encontrando telefone pelo numero do contato!");

        var entity = _repository.findByContato(contato);

        if (entity == null)
            throw new ResourceNotFoundException("Contato não encontrado");

        return DozerMapper.parseObject(entity, TelefoneVO.class);
    }

    /**
     * Método responsável por criar um telefone, contém validação
     * se telefone já estar cadastrado para algum cliente e
     * verificação se telefone é validado e não nulo
     * 
     * @param telefone Objeto com dados do telefone para inserção
     * @return Retorna um TelefoneVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public TelefoneVO create(TelefoneVO telefone) {
        logger.info("Criando novo telefone!");

        if (telefone.getCliente() == null)
            throw new ResourceBadRequestException("Cliente não pode ser nulo!");

        var entity = DozerMapper.parseObject(telefone, Telefone.class);

        var validTelefone = _repository.findByContato(entity.getContato());

        if (validTelefone != null)
            throw new ResourceBadRequestException("Telefone já cadastrado");
        if (telefone.getContato() == null || !validarTelefone(telefone.getContato()))
            throw new ResourceBadRequestException("Telefone inválido ou vazio!");

        var vo = DozerMapper.parseObject(_repository.save(entity), TelefoneVO.class);

        return vo;
    }

    /**
     * Método responsável por atualizar um telefone, contém validação
     * se telefone já estar cadastrado para algum cliente e
     * verificação se telefone é validado e não nulo
     * 
     * @param telefone Objeto com dados do telefone para inserção
     * @return Retorna um TelefoneVO
     * @author Wallison Junior Cardoso Soares Silva
     */
    public TelefoneVO update(TelefoneVO telefone) {
        logger.info("Atualizando um telefone!");

        if (telefone.getCliente() == null)
            throw new ResourceBadRequestException("Cliente não pode ser nulo!");

        var entity = _repository.findById(telefone.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));

        var validTelefone = _repository.findByContato(entity.getContato());

        if (validTelefone.getContato() == entity.getContato())
            throw new ResourceBadRequestException("Telefone já cadastrado!");
        if (!validarTelefone(telefone.getContato()))
            throw new ResourceBadRequestException("Telefone inválido!");

        var cliente = DozerMapper.parseObject(telefone.getCliente(), Cliente.class);

        var validarNulo = new ValidarNulo();

        entity.setContato(validarNulo.naoNulo(telefone.getContato(), entity.getContato()));
        entity.setCliente(cliente);

        var vo = DozerMapper.parseObject(_repository.save(entity), TelefoneVO.class);

        return vo;
    }

    /**
     * Método responsável por realizar deleção de um telefone
     * 
     * @param id Id do telefone que deseja deletar
     * @author Wallison Junior Cardoso Soares Silva
     */
    public void delete(Long id) {
        logger.info("Deletando telefone!");

        var entity = _repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado!"));

        _repository.delete(entity);
    }

    /**
     * Método para validação de número de telefone, se estar no formato (99) 9
     * 9999-9999 ou
     * (99) 9999-9999
     * 
     * @param telefone Número de telefone que deve ser validado
     * @return Retorna True/False para validação do telefone
     * @author Wallison Junior Cardoso Soares Silva
     */
    private static boolean validarTelefone(String telefone) {
        // Formato esperado: (XX) 9 9999-9999 ou (XX) 9999-9999
        String regex = "\\(\\d{2}\\)\\s?9?\\s?\\d{4}-\\d{4}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telefone);

        return matcher.matches();
    }
}
