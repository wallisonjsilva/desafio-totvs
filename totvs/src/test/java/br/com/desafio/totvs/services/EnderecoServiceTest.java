package br.com.desafio.totvs.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.desafio.totvs.data.vo.v1.ClienteVO;
import br.com.desafio.totvs.data.vo.v1.EnderecoVO;
import br.com.desafio.totvs.exceptions.ResourceBadRequestException;
import br.com.desafio.totvs.exceptions.ResourceNotFoundException;
import br.com.desafio.totvs.mapper.DozerMapper;
import br.com.desafio.totvs.model.Cliente;
import br.com.desafio.totvs.model.Endereco;
import br.com.desafio.totvs.repositories.ClienteRepository;
import br.com.desafio.totvs.repositories.EnderecoRepository;

/**
 * Classe responsável por realizar os tes do Endereco Service
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {

    @InjectMocks
    EnderecoService service;

    @InjectMocks
    ClienteService clienteService;

    @Mock
    EnderecoRepository repository;
    @Mock
    ClienteRepository clienteRepository;

    EnderecoVO enderecoVO;
    ClienteVO clienteVO;

    /**
     * Método para Mock dos dados para teste
     * 
     * @throws ParseException       Exceção caso não consiga converter data
     * @author                      Wallison Junior Cardoso Soares Silva
     */
    @BeforeEach
    public void setUp() throws ParseException {
        String dataString = "1975-05-08";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        Date data;

        data = formato.parse(dataString);

        clienteVO = new ClienteVO();
        clienteVO.setId(3L);
        clienteVO.setNome("Maria Batista Silva");
        clienteVO.setDataNascimento(data);
        clienteVO.setSexo("F");

        enderecoVO = new EnderecoVO();
        enderecoVO.setId(3L);
        enderecoVO.setBairro("Parque Amazonia");
        enderecoVO.setCep("74840-540");
        enderecoVO.setCidade("Goiania");
        enderecoVO.setEstado("GO");
        enderecoVO.setLogradouro("Rua X Qd 99 Lt 01/16");
        enderecoVO.setNumero(99);
        enderecoVO.setPais("Brasil");
        enderecoVO.setCliente(clienteVO);
    }

    /**
     * Método de teste pra verificar todos enderecos cadastrados
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosEnderecosCadastrados() {
        var endereco = DozerMapper.parseObject(enderecoVO, Endereco.class);

        when(repository.findAll()).thenReturn(Collections.singletonList(endereco));

        List<EnderecoVO> enderecos = service.findAll();

        assertEquals(Collections.singletonList(enderecoVO), enderecos);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para validar todos enderecos cadastrados com retorno
     * vazio
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosEnderecosCadastradosComRetornoVazio() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<EnderecoVO> enderecos = service.findAll();

        assertEquals(Collections.emptyList(), enderecos);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste pra verificar todos enderecos cadastrados por cliente
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosEnderecosCadastradosPorCliente() {
        var endereco = DozerMapper.parseObject(enderecoVO, Endereco.class);
        var cliente = DozerMapper.parseObject(clienteVO, Cliente.class);
        
        when(clienteRepository.findById(3L)).thenReturn(Optional.of(cliente));
        when(repository.findByCliente(cliente)).thenReturn(Collections.singletonList(endereco));

        List<EnderecoVO> enderecos = service.findByClienteId(3L);

        assertEquals(Collections.singletonList(enderecoVO), enderecos);
        verify(repository).findByCliente(cliente);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para validar todos enderecos cadastrados com retorno
     * vazio por cliente
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosEnderecosCadastradosComRetornoVazioPorCliente() {
        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.findById(null);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nenhuma informação encontrada!");
        verify(clienteRepository).findById(null);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Metodo de teste para buscar endereco por id
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarEnderecoPorId() {
        var endereco = DozerMapper.parseObject(enderecoVO, Endereco.class);
        var enderecoOptional = Optional.of(endereco);

        when(repository.findById(2L)).thenReturn(enderecoOptional);

        EnderecoVO enderecoResult = service.findById(2L);

        assertEquals(enderecoVO, enderecoResult);
        verify(repository).findById(2L);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Metodo de teste para buscar endereco por id e retornar nao encontrado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarEnderecoPorIdRetornarNaoEncontrado() {
        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(null);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nenhuma informação encontrada!");
        verify(repository).findById(null);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para tentar criar novo endereço para cliente
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarCriarNovoEnderecoParaCliente() {
        var endereco = DozerMapper.parseObject(enderecoVO, Endereco.class);

        when(repository.save(endereco)).thenReturn(endereco);

        EnderecoVO enderecoVOCriado = service.create(enderecoVO);

        assertEquals(enderecoVO, enderecoVOCriado);
        verify(repository).save(endereco);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para tentar endereço para cliente e retornar bad request
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveRetornarThrowAoTentarCriarNovoEnderecoParaCliente() {
        enderecoVO.setCliente(null);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.create(enderecoVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Cliente não pode ser nulo!");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para tentar criar novo endereço com campo obrigatorio nulo
     * e retornar bad request
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveRetornarThrowAtoTentarCriarNovoEnderecoComCampObrigatorioNulo() {
        EnderecoVO novoEnderecoVO = new EnderecoVO();
        novoEnderecoVO.setCliente(clienteVO);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.create(novoEnderecoVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Endereço incompleto!");
        verify(repository, never()).save(any(Endereco.class));
    }

    /**
     * Método de teste para retornar bad request caso cleinte no cadastrado de endereço
     * seja nulo
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveRetornarBadRequestCasoClienteNulo() {
        enderecoVO.setCliente(null);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.update(enderecoVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Cliente não pode ser nulo!");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para retornar nenhum registro encontrado na tentativa de realizar
     * atualização de endereço
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveRetornarNenhumRegistroEncontradoParaTentativaUpdate() {
        enderecoVO.setId(null);

        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(enderecoVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nenhum registro encontrado");
        verify(repository).findById(null);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para retornar sucesso na tentativa de realizar
     * atualização do endereço do cliente
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveRetornarObjetoCasoTentaConcluidoUpdateComSucesso() {
        var endereco = DozerMapper.parseObject(enderecoVO, Endereco.class);
        var enderecoOptional = Optional.of(endereco);

        when(repository.findById(3L)).thenReturn(enderecoOptional);

        EnderecoVO enderecoResult = service.findById(3L);

        assertEquals(enderecoVO, enderecoResult);
        verify(repository).findById(3L);

        when(repository.save(endereco)).thenReturn(endereco);

        EnderecoVO enderecoVOCriado = service.update(enderecoVO);

        assertEquals(enderecoVO, enderecoVOCriado);
        verify(repository).save(endereco);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para deletar registro e retornar not found
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveRetornarNotFoundNaTentativaDeletarRegistro() {
        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(null);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nenhum dado encontrado!");
        verify(repository).findById(null);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para deletar com sucesso um endereço
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveValidarSeDelecaoOcorreComSucesso() {
        var endereco = DozerMapper.parseObject(enderecoVO, Endereco.class);

        when(repository.findById(2L)).thenReturn(Optional.of(endereco));

        service.delete(2L);

        verify(repository).delete(endereco);
        verifyNoMoreInteractions(repository);
    }
}
