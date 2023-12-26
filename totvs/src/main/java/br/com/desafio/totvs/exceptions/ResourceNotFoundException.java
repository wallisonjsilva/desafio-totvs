package br.com.desafio.totvs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe para tratamento de exceção NOT_FOUND
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Serial number exception
     */
    private static final long serialVersionUID = 1L;

    /**
     * Método Construtor resource not found exception
     * @param ex    Exception
     */
    public ResourceNotFoundException(String ex) {
        super(ex);
    }
}
