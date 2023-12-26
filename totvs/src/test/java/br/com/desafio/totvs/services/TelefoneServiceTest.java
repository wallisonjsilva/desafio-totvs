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
import br.com.desafio.totvs.data.vo.v1.TelefoneVO;
import br.com.desafio.totvs.exceptions.ResourceBadRequestException;
import br.com.desafio.totvs.exceptions.ResourceNotFoundException;
import br.com.desafio.totvs.mapper.DozerMapper;
import br.com.desafio.totvs.model.Telefone;
import br.com.desafio.totvs.repositories.TelefoneRepository;

/**
 * Classe responsável por realizar os tes do Telefone Service
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@ExtendWith(MockitoExtension.class)
public class TelefoneServiceTest {

    @InjectMocks
    TelefoneService service;

    @Mock
    TelefoneRepository repository;

    TelefoneVO telefoneVO;
    ClienteVO clienteVO;

    /**
     * Método de teste pra verificar todos clientes cadastrados
     * 
     * @author  Wallison Junior Cardoso Soares Silva
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

        telefoneVO = new TelefoneVO();
        telefoneVO.setId(1L);
        telefoneVO.setContato("(62) 9 8261-5427");
        telefoneVO.setCliente(clienteVO);
    }

    /**
     * Método para testar telefones cadastrados e retornar objetos encontrado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosTelefonesCadastrados() {
        var telefone = DozerMapper.parseObject(telefoneVO, Telefone.class);

        when(repository.findAll()).thenReturn(Collections.singletonList(telefone));

        List<TelefoneVO> telefones = service.findAll();

        assertEquals(Collections.singletonList(telefoneVO), telefones);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para buscar todos telefones e retornar vazio
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosTelefonesCadastradosComRetornoVazio() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<TelefoneVO> telefones = service.findAll();

        assertEquals(Collections.emptyList(), telefones);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para buscar telefone por id
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTelefonePorId() {
        var telefone = DozerMapper.parseObject(telefoneVO, Telefone.class);

        when(repository.findById(2L)).thenReturn(Optional.of(telefone));

        TelefoneVO telefoneResult = service.findById(2L);

        assertEquals(telefoneVO, telefoneResult);
        verify(repository).findById(2L);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para buscar telefone por id e retornar not found
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTelefonePorIdRetornarNaoEncontrado() {
        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(null);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nenhuma informação encontrada!");
        verify(repository).findById(null);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para buscar telefone por numero do contato e retornar
     * encontrado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTelefonePorNumeroContatoRetornarEncontrado() {
        var telefone = DozerMapper.parseObject(telefoneVO, Telefone.class);

        when(repository.findByContato("(62) 9 8261-5427")).thenReturn(telefone);

        TelefoneVO telefoneResult = service.findByTelefone("(62) 9 8261-5427");

        assertEquals(telefoneVO, telefoneResult);
        verify(repository).findByContato("(62) 9 8261-5427");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para buscar telefone por numero de contato e retornar not found
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTelefonePorNumeroContatoRetornarNotFound() {
        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            service.findByTelefone("(63) 9 9999-9999");
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Contato não encontrado");
        verify(repository).findByContato("(63) 9 9999-9999");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para criar novo telefone para um cliente nulo e retornar
     * bad request
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarCriarNovoTelefoneParaClienteVazioRetornaBadRequest() {
        telefoneVO.setCliente(null);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.create(telefoneVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Cliente não pode ser nulo!");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para tentar criar novo telefone para cliente e retornar
     * bad request se ja existe
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarCriarNovoTelefoneParaClienteRetornarBadRequestSeExiste() {
        var telefone = DozerMapper.parseObject(telefoneVO, Telefone.class);

        when(repository.findByContato("(62) 9 8261-5427")).thenReturn(telefone);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.create(telefoneVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Telefone já cadastrado");
        verify(repository).findByContato("(62) 9 8261-5427");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para criar novo telefone para cliente e retornar
     * telefone invalido
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarCriarNovoTelefoneParaClienteRetornaTelefoneInvalido() {
        TelefoneVO telefoneNovo = new TelefoneVO();
        telefoneNovo.setContato("telefone invalido");
        telefoneNovo.setCliente(clienteVO);

        when(repository.findByContato(telefoneNovo.getContato())).thenReturn(null);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.create(telefoneNovo);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Telefone inválido ou vazio!");
        verify(repository, never()).save(any(Telefone.class));
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para criar novo telefone para cliente e retornar telefone vazio
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarCriarNovoTelefoneParaClienteRetornaTelefoneVazio() {
        TelefoneVO telefoneNovo = new TelefoneVO();
        telefoneNovo.setContato(null);
        telefoneNovo.setCliente(clienteVO);

        when(repository.findByContato(telefoneNovo.getContato())).thenReturn(null);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.create(telefoneNovo);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Telefone inválido ou vazio!");
        verify(repository, never()).save(any(Telefone.class));
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para criar novo telefone para cliente e retornar telefone criado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarCriarNovoTelefoneParaClienteRetornaTelefoneCriado() {
        TelefoneVO telefoneNovo = new TelefoneVO();
        telefoneNovo.setContato("(62) 9 9955-5533");
        telefoneNovo.setCliente(clienteVO);

        var telefone = DozerMapper.parseObject(telefoneNovo, Telefone.class);

        when(repository.findByContato(telefone.getContato())).thenReturn(null);

        when(repository.save(telefone)).thenReturn(telefone);

        TelefoneVO telefoneVOCriado = service.create(telefoneNovo);

        assertEquals(telefoneNovo, telefoneVOCriado);
        verify(repository).save(telefone);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para atualizar telefone para cliente vazio e retornar
     * bad request
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarAtualizarTelefoneParaClienteVazioRetornaBadRequest() {
        telefoneVO.setCliente(null);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.update(telefoneVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Cliente não pode ser nulo!");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para atualizar telefone para cliente e retornar nenhum registro
     * encontrado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarAtualizarTelefoneParaClienteRetornaNenhumRegistroEncontrado() {
        telefoneVO.setId(null);

        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(telefoneVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Nenhum registro encontrado");
        verify(repository).findById(null);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para atualizar telfone para cliente e retornar
     * telefone cadastrado
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarAtualizarTelefoneParaClienteRetornarTelefoneCadastrado() {
        var telefone = DozerMapper.parseObject(telefoneVO, Telefone.class);

        when(repository.findById(telefoneVO.getId())).thenReturn(Optional.of(telefone));
        when(repository.findByContato(telefoneVO.getContato())).thenReturn(telefone);

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.update(telefoneVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Telefone já cadastrado!");
        verify(repository).findByContato("(62) 9 8261-5427");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para atualizar telefone para cliente e retornar telefone invalido
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveTentarAtualizarTelefoneParaClienteRetornarTelefoneInvalido() {
        telefoneVO.setContato("telefoneInvalido");
        var telefone = DozerMapper.parseObject(telefoneVO, Telefone.class);

        when(repository.findById(1L)).thenReturn(Optional.of(telefone));
        when(repository.findByContato("telefoneInvalido")).thenReturn(new Telefone());

        final ResourceBadRequestException e = assertThrows(ResourceBadRequestException.class, () -> {
            service.update(telefoneVO);
        });

        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("Telefone inválido!");
        verify(repository).findById(1L);
        verify(repository).findByContato("telefoneInvalido");
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para retornar telefone caso tenta atualizado com sucesso
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveRetornarObjetoCasoTentaConcluidoUpdateComSucesso() {
        var telefone = DozerMapper.parseObject(telefoneVO, Telefone.class);

        when(repository.findById(1L)).thenReturn(Optional.of(telefone));
        when(repository.findByContato(telefoneVO.getContato())).thenReturn(new Telefone());

        TelefoneVO telefoneResult = service.findById(1L);

        assertEquals(telefoneVO, telefoneResult);
        verify(repository).findById(1L);

        when(repository.save(telefone)).thenReturn(telefone);

        TelefoneVO telefoneVOCriado = service.update(telefoneVO);

        assertEquals(telefoneVO, telefoneVOCriado);
        verify(repository).save(telefone);
        verifyNoMoreInteractions(repository);
    }

    /**
     * Método de teste para tentar deletar telefone e retorna not found
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
     * Método de teste apra tentar deletar um telefone e retornar
     * sucesso
     * 
     * @author  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveValidarSeDelecaoOcorreComSucesso() {
        var telefone = DozerMapper.parseObject(telefoneVO, Telefone.class);

        when(repository.findById(1L)).thenReturn(Optional.of(telefone));

        service.delete(1L);

        verify(repository).delete(telefone);
        verifyNoMoreInteractions(repository);
    }
}
