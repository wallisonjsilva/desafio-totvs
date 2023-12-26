package br.com.desafio.totvs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.totvs.data.vo.v1.ClienteVO;
import br.com.desafio.totvs.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Classe do controller cliente para disponibilizar acesso aos métodos
 * de interação com sistema 
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@RestController
@RequestMapping("/api/cliente/v1")
@Tag(name="Cliente", description = "Endpoint para Cliente")
public class ClienteController {
    
    /**
     * Chamada a classe service do cliente
     */
    @Autowired
    private ClienteService _service;

    /**
     * Método GET responsável por retornar todos clientes cadastrados no sistema
     * 
     * @return      Retorna List ResponseEntity assinado do tipo JSON
     * @author      Wallison Junior Cardoso Soares Silva
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Busca todos clientes", 
        description = "Busca todos clientes",
        tags = {"Cliente"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ClienteVO.class))
                )
            }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<List<ClienteVO>> findAll() {
        return ResponseEntity.ok(_service.findAll());
    }

    /**
     * Método GET responsável por retornar cliente por Id cadastrado no sistema
     * 
     * @param id    Id do cliente que deseja ser buscado
     * @return      Retorna ResponseEntity assinado do tipo JSON
     * @author      Wallison Junior Cardoso Soares Silva
     */
    @GetMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Busca um cliente", 
        description = "Busca um cliente",
        tags = {"Cliente"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClienteVO.class)
                )
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<ClienteVO> findById(@PathVariable(value = "id") Long id) {
        ClienteVO cliente = _service.findById(id);
        if (cliente == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(cliente);
    }

    /**
     * Método POST responsável por inserir um novo cliente no sistema
     * 
     * @param cliente     Objeto com dados do cliente para inserção
     * @return            Retorna ResponseEntity assinado do tipo JSON
     * @author            Wallison Junior Cardoso Soares Silva
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Insere um novo cliente", 
        description = "Insere um novo cliente passado via JSON",
        tags = {"Cliente"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClienteVO.class)
                )
            }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<ClienteVO> create(@RequestBody ClienteVO cliente) {
        ClienteVO clienteVO = _service.create(cliente);
        if (clienteVO == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(clienteVO);
    }

    /**
     * Método PUT responsável por atualizar um cliente no sistema
     * 
     * @param cliente     Objeto com dados do cliente para atualização
     * @return            Retorna ResponseEntity assinado do tipo JSON
     * @author            Wallison Junior Cardoso Soares Silva
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualiza um cliente", 
        description = "Atualiza um cliente passado via JSON",
        tags = {"Cliente"},
        responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClienteVO.class)
                )
            }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<ClienteVO> update(@RequestBody ClienteVO cliente) {
        ClienteVO clienteVO = _service.update(cliente);
        if (clienteVO == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(clienteVO);
    }

    /**
     * Método DELETE responsável por deletar um cliente no sistema
     * 
     * @param id     Id do cliente que deseja ser deletado
     * @return       Retorna no content
     * @author       Wallison Junior Cardoso Soares Silva
     */
    @DeleteMapping(value = "/{id}")
    @Operation(
        summary = "Deleta um cliente", 
        description = "Deleta um cliente passado via JSON",
        tags = {"Cliente"},
        responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        if (id == null)
            return ResponseEntity.badRequest().build();
            
        _service.delete(id);
        return ResponseEntity.noContent().build();
    }
}