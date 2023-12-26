package br.com.desafio.totvs.exceptions;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe com as informações que é enviada no disparado de 
 * uma exceção customizada
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
public class ExceptionResponse implements Serializable {

    /**
     * Serial number exception response
     */
    private static final long serialVersionUID = 1L;

    /**
     * Timestamp exception
     */
    private Date timestamp;

    /**
     * Messagem exception
     */
    private String message;
    /**
     * Detalhes exception
     */
    private String details;

    /**
     * Construtor exception response
     * @param timestamp Timestamp exception
     * @param message   Messagem exception
     * @param details   Detalhes exception
     */
    public ExceptionResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
    
    /**
     * Método get timestamp
     * @return  Retorna timestamp exception
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Método get message
     * @return  Retorna message exception
     */
    public String getMessage() {
        return message;
    }

    /**
     * Método get details exception
     * @return  Retorna message exception
     */
    public String getDetails() {
        return details;
    }
}
