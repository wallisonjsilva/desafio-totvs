package br.com.desafio.totvs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.totvs.data.vo.v1.EnderecoVO;
import br.com.desafio.totvs.services.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe do controller endereço para disponibilizar acesso aos métodos
 * de interação com sistema 
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@RestController
@RequestMapping("/api/endereco/v1")
@Tag(name="Endereco", description = "Endpoint para Endereco")
public class EnderecoController {
    /**
     * Instancia do endereço service
     */
    @Autowired
    private EnderecoService _service;

    /**
     * Método GET responsável por retornar todos endereços cadastrados no sistema
     * 
     * @return      Retorna List ResponseEntity assinado do tipo JSON
     * @author      Wallison Junior Cardoso Soares Silva
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Busca todos enderecos", 
        description = "Busca todos enderecos",
        tags = {"Endereco"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = EnderecoVO.class))
                )
            }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<List<EnderecoVO>> findAll() {
        return ResponseEntity.ok(_service.findAll());
    }

    /**
     * Método GET responsável por retornar endereço por Id cadastrado no sistema
     * 
     * @param id    Id do endereço que deseja ser buscado
     * @return      Retorna ResponseEntity assinado do tipo JSON
     * @author      Wallison Junior Cardoso Soares Silva
     */
    @GetMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Busca um endereco", 
        description = "Busca um endereco",
        tags = {"Endereco"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EnderecoVO.class)
                )
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<EnderecoVO> findById(@PathVariable(value = "id") Long id) {
        EnderecoVO enderecoVO = _service.findById(id);
        if (enderecoVO == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(enderecoVO);
    }

    /**
     * Método GET responsável por retornar endereço por Cliente Id cadastrado no sistema
     * 
     * @param clienteId    Id do cliente que deseja ser buscado
     * @return      Retorna ResponseEntity assinado do tipo JSON
     * @author      Wallison Junior Cardoso Soares Silva
     */
    @GetMapping(value = "/cliente/{clienteId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Busca um endereco", 
        description = "Busca um endereco",
        tags = {"Endereco"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EnderecoVO.class)
                )
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<List<EnderecoVO>> findBClienteyId(@PathVariable(value = "clienteId") Long clienteId) {
        List<EnderecoVO> enderecoVO = _service.findByClienteId(clienteId);
        if (enderecoVO == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(enderecoVO);
    }

    /**
     * Método POST responsável por inserir um novo endereço no sistema
     * 
     * @param endereco        Objeto com dados do endereço para inserção
     * @return                  Retorna ResponseEntity assinado do tipo JSON
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Insere um novo endereco", 
        description = "Insere um novo endereco passado via JSON",
        tags = {"Endereco"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EnderecoVO.class)
                )
            }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<EnderecoVO> create(@RequestBody EnderecoVO endereco) {
        EnderecoVO enderecoVO = _service.create(endereco);
        if (enderecoVO == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(enderecoVO);
    }

    /**
     * Método PUT responsável por atualizar um endereço no sistema
     * 
     * @param endereco        Objeto com dados do endereço para atualização
     * @return                  Retorna ResponseEntity assinado do tipo JSON
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualiza um endereco", 
        description = "Atualiza um endereco passado via JSON",
        tags = {"Endereco"},
        responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EnderecoVO.class)
                )
            }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<EnderecoVO> update(@RequestBody EnderecoVO endereco) {
        EnderecoVO enderecoVO =  _service.update(endereco);
        if (enderecoVO == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(enderecoVO);
    }

    /**
     * Método DELETE responsável por deletar um endereço no sistema
     * 
     * @param id     Id do endereço que deseja ser deletado
     * @return        retornar no content
     * @author       Wallison Junior Cardoso Soares Silva
     */
    @DeleteMapping(value = "/{id}")
    @Operation(
        summary = "Deleta um endereco", 
        description = "Deleta um endereco passado via JSON",
        tags = {"Endereco"},
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
