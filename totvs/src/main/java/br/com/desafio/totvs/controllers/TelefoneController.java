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

import br.com.desafio.totvs.data.vo.v1.TelefoneVO;
import br.com.desafio.totvs.services.TelefoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe do controller telefone para disponibilizar acesso aos métodos
 * de interação com sistema 
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@RestController
@RequestMapping("/api/telefone/v1")
@Tag(name="Telefone", description = "Endpoint para Telefone")
public class TelefoneController {
    /**
     * Instancia telefone service
     */
    @Autowired
    private TelefoneService _service;

    /**
     * Método GET responsável por retornar todos telefones cadastrados no sistema
     * 
     * @return      Retorna List ResponseEntity assinado do tipo JSON
     * @author      Wallison Junior Cardoso Soares Silva
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Busca todos telefones", 
        description = "Busca todos telefones",
        tags = {"Telefone"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TelefoneVO.class))
                )
            }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<List<TelefoneVO>> findAll() {
        return ResponseEntity.ok(_service.findAll());
    }

    /**
     * Método GET responsável por retornar telefone por Id cadastrado no sistema
     * 
     * @param telefone    Contato que deseja ser buscado
     * @return      Retorna ResponseEntity assinado do tipo JSON
     * @author      Wallison Junior Cardoso Soares Silva
     */
    @GetMapping(value = "/contato/{telefone}",
			produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Busca um telefone por numero de contato", 
        description = "Busca um telefone por numero de contato",
        tags = {"Telefone"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TelefoneVO.class)
                )
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<TelefoneVO> findByTelefone(@PathVariable(value = "telefone") String telefone) {
        TelefoneVO telefoneVO = _service.findByTelefone(telefone);
        if (telefoneVO == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(telefoneVO);
    }

    /**
     * Método GET responsável por retornar telefone por cliente Id cadastrado no sistema
     * 
     * @param clienteId    Cliente id que deseja ser buscado
     * @return      Retorna ResponseEntity assinado do tipo JSON
     * @author      Wallison Junior Cardoso Soares Silva
     */
    @GetMapping(value = "/cliente/{clienteId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Busca um telefone por numero de contato", 
        description = "Busca um telefone por numero de contato",
        tags = {"Telefone"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TelefoneVO.class)
                )
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<List<TelefoneVO>> findByClienteId(@PathVariable(value = "clienteId") Long clienteId) {
        List<TelefoneVO> telefoneVO = _service.findByClienteId(clienteId);
        if (telefoneVO == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(telefoneVO);
    }

    /**
     * Método GET responsável por retornar telefone por Id cadastrado no sistema
     * 
     * @param id    Id do telefone que deseja ser buscado
     * @return      Retorna ResponseEntity assinado do tipo JSON
     * @author      Wallison Junior Cardoso Soares Silva
     */
    @GetMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Busca um telefone", 
        description = "Busca um telefone",
        tags = {"Telefone"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TelefoneVO.class)
                )
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<TelefoneVO> findById(@PathVariable(value = "id") Long id) {
        TelefoneVO telefoneVO = _service.findById(id);
        if (telefoneVO == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(telefoneVO);
    }

    /**
     * Método POST responsável por inserir um novo telefone no sistema
     * 
     * @param telefone        Objeto com dados do telefone para inserção
     * @return                  Retorna ResponseEntity assinado do tipo JSON
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Insere um novo telefone", 
        description = "Insere um novo telefone passado via JSON",
        tags = {"Telefone"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TelefoneVO.class)
                )
            }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<TelefoneVO> create(@RequestBody TelefoneVO telefone) {
        TelefoneVO telefoneVO = _service.create(telefone);
        if (telefoneVO == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(telefoneVO);
    }

    /**
     * Método PUT responsável por atualizar um telefone no sistema
     * 
     * @param telefone        Objeto com dados do telefone para atualização
     * @return                  Retorna ResponseEntity assinado do tipo JSON
     * @author                  Wallison Junior Cardoso Soares Silva
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualiza um telefone", 
        description = "Atualiza um telefone passado via JSON",
        tags = {"Telefone"},
        responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TelefoneVO.class)
                )
            }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<TelefoneVO> update(@RequestBody TelefoneVO telefone) {
        TelefoneVO telefoneVO = _service.update(telefone);
        if (telefoneVO == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(telefoneVO);
    }

    /**
     * Método DELETE responsável por deletar um telefone no sistema
     * 
     * @param id     Id do telefone que deseja ser deletado
     * @return          Retorna No content
     * @author       Wallison Junior Cardoso Soares Silva
     */
    @DeleteMapping(value = "/{id}")
    @Operation(
        summary = "Deleta um telefone", 
        description = "Deleta um telefone passado via JSON",
        tags = {"Telefone"},
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
