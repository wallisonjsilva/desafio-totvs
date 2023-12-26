package br.com.desafio.totvs.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio.totvs.data.vo.v1.ClienteVO;
import br.com.desafio.totvs.data.vo.v1.EnderecoVO;
import br.com.desafio.totvs.services.EnderecoService;

/**
 * Classe responsável por realizar os tes do Endereco Controller
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@ExtendWith(MockitoExtension.class)
public class EnderecoControllerTest {
    @InjectMocks
    EnderecoController controller;

    @Mock
    private EnderecoService service;

    MockMvc mockMvc;

    private EnderecoVO enderecoVO;
    private ClienteVO clienteVO;
    private Long id;

    /**
     * Método para Mock dos dados para teste
     * 
     * @throws ParseException       Exceção caso não consiga converter data
     * @author                      Wallison Junior Cardoso Soares Silva
     */
    @BeforeEach
    public void setUp() throws ParseException {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .alwaysDo(print())
                .build();

        String dataString = "1975-05-08";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        Date data;

        data = formato.parse(dataString);

        clienteVO = new ClienteVO();
        clienteVO.setId(2L);
        clienteVO.setNome("Maria Batista Silva");
        clienteVO.setDataNascimento(data);
        clienteVO.setSexo("F");

        enderecoVO = new EnderecoVO();
        enderecoVO.setId(2L);
        enderecoVO.setBairro("Parque Amazonia");
        enderecoVO.setCep("74840-540");
        enderecoVO.setCidade("Goiania");
        enderecoVO.setEstado("GO");
        enderecoVO.setLogradouro("Rua X Qd 99 Lt 01/16");
        enderecoVO.setNumero(99);
        enderecoVO.setPais("Brasil");
        enderecoVO.setCliente(clienteVO);

        id = 2L;
    }

    /**
     * Método para testar endereco, deve buscar todos enderecos inseridos no sistema
     * e retornar sucesso
     * 
     * @throws Exception        Exceção na tentativa de invocar método get
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosEnderecosInseridosNoBanco() throws Exception {
        when(service.findAll()).thenReturn(Collections.singletonList(enderecoVO));

        mockMvc.perform(get("/api/endereco/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve buscar todos enderecos inseridos no sistema
     * por cliente
     * e retornar sucesso
     * 
     * @throws Exception Exceção na tentativa de invocar método get
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosTelefonesInseridosNoBancoPorCliente() throws Exception {
        when(service.findByClienteId(2L)).thenReturn(Collections.singletonList(enderecoVO));

        mockMvc.perform(get("/api/endereco/v1/cliente/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).findByClienteId(clienteVO.getId());
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar endereco, deve inserir novo endereco e retornar
     * sucesso
     * 
     * @throws Exception        Exceção na tentativa de invocar método post
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarEnderecoPorIdRetornarEncontrado() throws Exception {
        when(service.findById(id)).thenReturn(enderecoVO);

        mockMvc.perform(get("/api/endereco/v1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar endereco, deve inserir novo endereco e retornar
     * not found
     * 
     * @throws Exception        Exceção na tentativa de invocar método post
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarEnderecoPorIdRetornarNotFound() throws Exception {
        when(service.findById(7L)).thenReturn(null);

        mockMvc.perform(get("/api/endereco/v1/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        verify(service).findById(7L);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar endereco, deve inserir novo endereco e retornar
     * sucesso
     * 
     * @throws Exception        Exceção na tentativa de invocar método post
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveInserirEnderecoRetornarSuccess() throws Exception {
        when(service.create(enderecoVO)).thenReturn(enderecoVO);

        ObjectMapper objectMapper = new ObjectMapper();
        String enderecoVOJson = objectMapper.writeValueAsString(enderecoVO);

        mockMvc.perform(post("/api/endereco/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(enderecoVOJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).create(enderecoVO);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar endereco, deve inserir novo endereco e retornar
     * bad request
     * 
     * @throws Exception        Exceção na tentativa de invocar método post
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveInserirEnderecoRetornarBadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String enderecoVOJson = objectMapper.writeValueAsString(EnderecoVO.class);

        mockMvc.perform(post("/api/endereco/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(enderecoVOJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar endereco, deve atualizar endereco e retornar
     * success
     * 
     * @throws Exception        Exceção na tentativa de invocar método put
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveAtualizarEnderecoRetornarSucess() throws Exception {
        when(service.update(enderecoVO)).thenReturn(enderecoVO);

        ObjectMapper objectMapper = new ObjectMapper();
        String enderecoVOJson = objectMapper.writeValueAsString(enderecoVO);

        mockMvc.perform(put("/api/endereco/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(enderecoVOJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).update(enderecoVO);
        verifyNoMoreInteractions(service);
    }
    
    /**
     * Método para testar endereco, deve atualizar endereco e retornar
     * bad request
     * 
     * @throws Exception        Exceção na tentativa de invocar método put
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveAtualizarEnderecoRetornarBadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String enderecoVOJson = objectMapper.writeValueAsString(EnderecoVO.class);

        mockMvc.perform(put("/api/endereco/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(enderecoVOJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar endereco, deve deletar endereco por id e retornar
     * no content
     * 
     * @throws Exception        Exceção na tentativa de invocar método delete
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveDeletarEnderecoRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/endereco/v1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        verify(service).delete(id);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar endereco, deve deletar endereco por id e retornar
     * bad request
     * 
     * @throws Exception        Exceção na tentativa de invocar método delete
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveDeletarEnderecoRetornarBadRequest() throws Exception {
        mockMvc.perform(delete("/api/endereco/v1/null")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyNoMoreInteractions(service);
    }
}
