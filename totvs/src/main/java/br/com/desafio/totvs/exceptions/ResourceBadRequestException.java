package br.com.desafio.totvs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe para tratamento de exceção BAD_REQUEST
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException {
    /**
     * Serial number tratamento exceção
     */
    private static final long serialVersionUID = 1L;

    /**
     * Método Construtor resource bad request exception
     * @param ex    exception
     */
    public ResourceBadRequestException(String ex) {
        super(ex);
    }
}
