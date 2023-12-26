package br.com.desafio.totvs.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import br.com.desafio.totvs.exceptions.ResourceBadRequestException;
import br.com.desafio.totvs.exceptions.ResourceNotFoundException;
import br.com.desafio.totvs.mapper.DozerMapper;
import br.com.desafio.totvs.model.Cliente;
import br.com.desafio.totvs.repositories.ClienteRepository;


/**
 * Classe responsável por realizar os tes do Cliente Service
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    ClienteService service;

    @Mock
    ClienteRepository repository;

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
        clienteVO.setId(2L);
        clienteVO.setNome("Maria Batista Silva");
        clienteVO.setDataNascimento(data);
        clienteVO.setSexo("F");
    }

    /**
     * Método de teste pra verificar todos clientes cadastrados
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosClientesCadastrados() {
        var cliente = DozerMapper.parseObject(clienteVO, Cliente.class);

        when(repository.findAll()).thenReturn(Collections.singletonList(cliente));

        List<ClienteVO> clientes = service.findAll();

        assertEquals(Collections.singletonList(clienteVO), clientes);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para verificar todos clientes com retorno vazio
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosClientesCadastradosComRetornoVazio() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<ClienteVO> clientes = service.findAll();

        assertEquals(Collections.emptyList(), clientes);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método responsável por testar busca cliente por id
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarClientePorId() {
        var cliente = DozerMapper.parseObject(clienteVO, Cliente.class);

        when(repository.findById(2L)).thenReturn(Optional.of(cliente));

        ClienteVO clienteResult = service.findById(2L);

        assertEquals(clienteVO, clienteResult);
        verify(repository).findById(2L);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método responsável por testar busca de cliente por id e retornar
     * não encontrado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarClientePorIdRetornarNaoEncontrado() {
        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(null);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nenhuma informação encontrada!");
        verify(repository).findById(null);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para validar nome curtor com retorno de bad request
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarCriarNovoClienteComNomeCurtoRetornaBadRequest() {
        clienteVO.setNome("nomeCurto");
        var cliente = DozerMapper.parseObject(clienteVO, Cliente.class);

        when(repository.findByNome(cliente.getNome())).thenReturn(null);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.create(clienteVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nome cliente no mínimo 11 caracteres!");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para validar se cliente já existe e retornar bad request
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarCriarNovoClienteQueJaExisteRetornaBadRequest() {
        var cliente = DozerMapper.parseObject(clienteVO, Cliente.class);

        when(repository.findByNome(cliente.getNome())).thenReturn(cliente);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.create(clienteVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Cliente já cadastrado!");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método para testar, criação novo cliente e retornar cliente criado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarCriarNovoClienteRetornarClienteCriado() {
        ClienteVO clienteNovo = new ClienteVO();
        clienteNovo.setNome("Joaquim Emanuel Fulano");
        clienteNovo.setDataNascimento(new Date());
        clienteNovo.setSexo("M");

        var cliente = DozerMapper.parseObject(clienteNovo, Cliente.class);

        when(repository.findByNome(cliente.getNome())).thenReturn(null);

        when(repository.save(cliente)).thenReturn(cliente);

        ClienteVO clienteVOCriado = service.create(clienteNovo);

        assertEquals(clienteNovo, clienteVOCriado);
        verify(repository).save(cliente);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para atualização de cliente  e retornar nenhum registro encontrado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarAtualizarClienteRetornarNenhumRegistroEncontrado() {
        clienteVO.setId(null);

        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(clienteVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nenhum registro encontrado");
        verify(repository).findById(null);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para atualização de cliente e retornar bad request para cliente
     * já cadastrado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarAtualizarClienteRetornarBadRequestClienteCadastrado() {
        var cliente = DozerMapper.parseObject(clienteVO, Cliente.class);

        when(repository.findById(clienteVO.getId())).thenReturn(Optional.of(cliente));

        when(repository.findByNome(clienteVO.getNome())).thenReturn(cliente);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.update(clienteVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Cliente já cadastrado!");
        verify(repository).findById(clienteVO.getId());
        verify(repository).findByNome(clienteVO.getNome());
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para atualização de cliente e retornar bad request para
     * nome curto
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarAtualizarClienteRetornarBadRequestClienteNomeCurto() {
        var cliente = DozerMapper.parseObject(clienteVO, Cliente.class);

        when(repository.findById(clienteVO.getId())).thenReturn(Optional.of(cliente));

        clienteVO.setNome("nomeCurto");

        when(repository.findByNome(clienteVO.getNome())).thenReturn(null);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.update(clienteVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nome cliente no mínimo 11 caracteres!");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para atualizar cliente e retornar cliente atualizado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarAtualizarClienteRetornarClienteAtualizado() {
        var cliente = DozerMapper.parseObject(clienteVO, Cliente.class);

        when(repository.findById(2L)).thenReturn(Optional.of(cliente));

        when(repository.findByNome(clienteVO.getNome())).thenReturn(null);

        ClienteVO clienteResult = service.findById(2L);

        assertEquals(clienteVO, clienteResult);
        verify(repository).findById(2L);

        when(repository.save(cliente)).thenReturn(cliente);

        ClienteVO clienteVOCriado = service.update(clienteVO);

        assertEquals(clienteVO, clienteVOCriado);
        verify(repository).save(cliente);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para tentar deletar registro e retornar not found
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
     * Método de teste para tentar deletar cliente e retornar sucesso
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveValidarSeDelecaoOcorreComSucesso() {
        var cliente = DozerMapper.parseObject(clienteVO, Cliente.class);

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        service.delete(1L);

        verify(repository).delete(cliente);
        verifyNoMoreInteractions(repository);
    }
}
