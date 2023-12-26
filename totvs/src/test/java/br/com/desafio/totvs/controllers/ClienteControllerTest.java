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
import br.com.desafio.totvs.services.ClienteService;

/**
 * Classe responsável por realizar os tes do Cliente Controller
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @InjectMocks
    ClienteController controller;

    @Mock
    private ClienteService service;

    MockMvc mockMvc;

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

        id = 2L;
    }

    /**
     * Método para testar cliente, deve buscar todos clientes inseridos no sistema
     * e retornar sucesso
     * 
     * @throws Exception        Exceção na tentativa de invocar método get
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosClientesInseridosNoBanco() throws Exception {
        when(service.findAll()).thenReturn(Collections.singletonList(clienteVO));

        mockMvc.perform(get("/api/cliente/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar cliente, deve buscar cliente por id e 
     * retornar sucesso
     * 
     * @throws Exception        Exceção na tentativa de invocar método get
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarClientePorIdRetornarEncontrado() throws Exception {
        when(service.findById(id)).thenReturn(clienteVO);

        mockMvc.perform(get("/api/cliente/v1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar cliente, deve buscar cliente por id e
     * retornar não encontrado
     * 
     * @throws Exception        Exceção na tentativa de invocar método get
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarClientePorIdRetornarNotFound() throws Exception {
        when(service.findById(7L)).thenReturn(null);

        mockMvc.perform(get("/api/cliente/v1/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        verify(service).findById(7L);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar cliente, deve inserir novo cliente e retornar
     * sucesso
     * 
     * @throws Exception        Exceção na tentativa de invocar método post
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveInserirClienteRetornarSuccess() throws Exception {
        when(service.create(clienteVO)).thenReturn(clienteVO);

        ObjectMapper objectMapper = new ObjectMapper();
        String clienteVOJson = objectMapper.writeValueAsString(clienteVO);

        mockMvc.perform(post("/api/cliente/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteVOJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).create(clienteVO);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar cliente, deve inserir novo cliente e retornar
     * bad request
     * 
     * @throws Exception        Exceção na tentativa de invocar método post
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveInserirClienteRetornarBadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String clienteVOJson = objectMapper.writeValueAsString(ClienteVO.class);

        mockMvc.perform(post("/api/cliente/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteVOJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar cliente, deve atualizar cliente e retornar
     * sucesso
     * 
     * @throws Exception        Exceção na tentativa de invocar método put
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveAtualizarClienteRetornarSucess() throws Exception {
        when(service.update(clienteVO)).thenReturn(clienteVO);

        ObjectMapper objectMapper = new ObjectMapper();
        String clienteVOJson = objectMapper.writeValueAsString(clienteVO);

        mockMvc.perform(put("/api/cliente/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteVOJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).update(clienteVO);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar cliente, deve atualizar cliente e retornar
     * bad requeset
     * 
     * @throws Exception        Exceção na tentativa de invocar método put
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveAtualizarClienteRetornarBadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String clienteVOJson = objectMapper.writeValueAsString(ClienteVO.class);

        mockMvc.perform(put("/api/cliente/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteVOJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar cliente, deve deletar cliente por id e retornar
     * no content
     * 
     * @throws Exception        Exceção na tentativa de invocar método delete
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveDeletarClienteRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/cliente/v1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        verify(service).delete(id);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar cliente, deve deletar cliente por id e retornar
     * bad request
     * 
     * @throws Exception        Exceção na tentativa de invocar método delete
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveDeletarClienteRetornarBadRequest() throws Exception {
        mockMvc.perform(delete("/api/cliente/v1/null")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyNoMoreInteractions(service);
    }
}
