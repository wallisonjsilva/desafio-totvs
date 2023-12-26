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
import br.com.desafio.totvs.data.vo.v1.TelefoneVO;
import br.com.desafio.totvs.services.TelefoneService;

/**
 * Classe responsável por realizar os tes do Telefone Controller
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
@ExtendWith(MockitoExtension.class)
public class TelefoneControllerTest {
    @InjectMocks
    TelefoneController controller;

    @Mock
    private TelefoneService service;

    MockMvc mockMvc;

    private TelefoneVO telefoneVO;
    private ClienteVO clienteVO;
    private Long id;
    private String telefone;

    /**
     * Método para Mock dos dados para teste
     * 
     * @throws ParseException Exceção caso não consiga converter data
     * @author Wallison Junior Cardoso Soares Silva
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
        clienteVO.setId(1L);
        clienteVO.setNome("Maria Batista Silva");
        clienteVO.setDataNascimento(data);
        clienteVO.setSexo("F");

        telefoneVO = new TelefoneVO();
        telefoneVO.setId(1L);
        telefoneVO.setContato("(62) 9 9999-9999");
        telefoneVO.setCliente(clienteVO);

        id = 1L;
        telefone = "(62) 9 9999-9999";
    }

    /**
     * Método para testar telefone, deve buscar todos telefones inseridos no sistema
     * e retornar sucesso
     * 
     * @throws Exception Exceção na tentativa de invocar método get
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosTelefonesInseridosNoBanco() throws Exception {
        when(service.findAll()).thenReturn(Collections.singletonList(telefoneVO));

        mockMvc.perform(get("/api/telefone/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve buscar todos telefones inseridos no sistema
     * por cliente
     * e retornar sucesso
     * 
     * @throws Exception Exceção na tentativa de invocar método get
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTodosTelefonesInseridosNoBancoPorCliente() throws Exception {
        when(service.findByClienteId(1L)).thenReturn(Collections.singletonList(telefoneVO));

        mockMvc.perform(get("/api/telefone/v1/cliente/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).findByClienteId(clienteVO.getId());
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve inserir novo telefone e retornar
     * sucesso
     * 
     * @throws Exception Exceção na tentativa de invocar método post
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTelefonePorIdRetornarEncontrado() throws Exception {
        when(service.findById(id)).thenReturn(telefoneVO);

        mockMvc.perform(get("/api/telefone/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve buscar telefone por numero contato e
     * retornar
     * not found
     * 
     * @throws Exception Exceção na tentativa de invocar método post
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTelefonePorContatoRetornarNotFound() throws Exception {
        when(service.findByTelefone("naoValido")).thenReturn(null);

        mockMvc.perform(get("/api/telefone/v1/contato/naoValido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        verify(service).findByTelefone("naoValido");
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve buscar telefone por numero de contato e
     * retornar
     * sucesso
     * 
     * @throws Exception Exceção na tentativa de invocar método post
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTelefonePorContatoRetornarEncontrado() throws Exception {
        when(service.findByTelefone(telefone)).thenReturn(telefoneVO);

        mockMvc.perform(get("/api/telefone/v1/contato/(62) 9 9999-9999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).findByTelefone(telefone);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve buscar telefone por id e retornar
     * not found
     * 
     * @throws Exception Exceção na tentativa de invocar método post
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveBuscarTelefonePorIdRetornarNotFound() throws Exception {
        when(service.findById(7L)).thenReturn(null);

        mockMvc.perform(get("/api/telefone/v1/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        verify(service).findById(7L);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve buscar telefone por id e retornar
     * sucesso
     * 
     * @throws Exception Exceção na tentativa de invocar método post
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveInserirTelefoneRetornarSuccess() throws Exception {
        when(service.create(telefoneVO)).thenReturn(telefoneVO);

        ObjectMapper objectMapper = new ObjectMapper();
        String telefoneVOJson = objectMapper.writeValueAsString(telefoneVO);

        mockMvc.perform(post("/api/telefone/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(telefoneVOJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).create(telefoneVO);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve inserir novo telefone e retornar
     * bad request
     * 
     * @throws Exception Exceção na tentativa de invocar método post
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveInserirTelefoneRetornarBadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String telefoneVOJson = objectMapper.writeValueAsString(TelefoneVO.class);

        mockMvc.perform(post("/api/telefone/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(telefoneVOJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve atualizar telefone e retornar
     * success
     * 
     * @throws Exception Exceção na tentativa de invocar método put
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveAtualizarTelefoneRetornarSucess() throws Exception {
        when(service.update(telefoneVO)).thenReturn(telefoneVO);

        ObjectMapper objectMapper = new ObjectMapper();
        String telefoneVOJson = objectMapper.writeValueAsString(telefoneVO);

        mockMvc.perform(put("/api/telefone/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(telefoneVOJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(service).update(telefoneVO);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve atualizar telefone e retornar
     * bad request
     * 
     * @throws Exception Exceção na tentativa de invocar método put
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveAtualizarTelefoneRetornarBadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String telefoneVOJson = objectMapper.writeValueAsString(TelefoneVO.class);

        mockMvc.perform(put("/api/telefone/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(telefoneVOJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve deletar telefone por id e retornar
     * no content
     * 
     * @throws Exception Exceção na tentativa de invocar método delete
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveDeletarTelefoneRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/telefone/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        verify(service).delete(id);
        verifyNoMoreInteractions(service);
    }

    /**
     * Método para testar telefone, deve deletar telefone por id e retornar
     * bad request
     * 
     * @throws Exception Exceção na tentativa de invocar método delete
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Test
    void deveDeletarTelefoneRetornarBadRequest() throws Exception {
        mockMvc.perform(delete("/api/telefone/v1/null")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyNoMoreInteractions(service);
    }
}
