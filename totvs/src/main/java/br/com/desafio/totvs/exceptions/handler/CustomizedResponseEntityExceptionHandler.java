package br.com.desafio.totvs.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.desafio.totvs.exceptions.ExceptionResponse;
import br.com.desafio.totvs.exceptions.ResourceNotFoundException;

/**
 * Classe responsável por fazer customização de exceções
 * 
 * @author	Wallison Junior Cardoso Soares Silva
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Método responsável por capturar exceção e retornar um INTERNAL_SERVER_ERROR
	 * 
	 * @param ex		Exceção disparada
	 * @param request	Request que disparou a exceção
	 * @return			retornar um INTERNAL_SERVER_ERROR no ResponseEntity
	 * @author 			Wallison Junior Cardoso Soares Silva
	 */
    @ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Método responsável por capturar exceção e retornar um NOT_FOUND
	 * 
	 * @param ex		Exceção disparada
	 * @param request	Request que disparou a exceção
	 * @return			retornar um NOT_FOUND no ResponseEntity
	 * @author 			Wallison Junior Cardoso Soares Silva
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
}
