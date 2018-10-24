package br.com.cursomvc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import br.com.cursomvc.services.exceptions.AuthorizationException;
import br.com.cursomvc.services.exceptions.DataIntegrityException;
import br.com.cursomvc.services.exceptions.FileException;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler{
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound (ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
												HttpStatus.NOT_FOUND.value(), 
												"Não encontrado", 
												e.getLocalizedMessage(),
												request.getRequestURI());
				
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrityException (DataIntegrityException e, HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
												HttpStatus.BAD_REQUEST.value(), 
												"Integridade de dados", 
												e.getLocalizedMessage(),
												request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValidException (MethodArgumentNotValidException e, HttpServletRequest request){
		
//		ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), 
//												"Erro de validação",   //e.getMessage() - mostra apenas a frase de erro de validação
//												System.currentTimeMillis());
		ValidationError error = new ValidationError(System.currentTimeMillis(), 
													HttpStatus.UNPROCESSABLE_ENTITY.value(), 
													"Erro de validação", 
													e.getLocalizedMessage(),
													request.getRequestURI());
		
		for(FieldError x  : e.getBindingResult().getFieldErrors()) { //percorre todos os erros de MethodArgumentNotValidException
			error.addError(x.getField(), x.getDefaultMessage()); //adiciona o nome do campo e a mensagem do erro
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorizationException (AuthorizationException e, HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
												HttpStatus.FORBIDDEN.value(), 
												"Acesso negado", 
												e.getLocalizedMessage(),
												request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> fileException (FileException e, HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
												HttpStatus.BAD_REQUEST.value(), 
												"Erro de arquivo", 
												e.getLocalizedMessage(),
												request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonServiceException (AmazonServiceException e, HttpServletRequest request){
		
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		StandardError error = new StandardError(System.currentTimeMillis(), 
												code.value(), 
												"Erro Amazon Service", 
												e.getLocalizedMessage(),
												request.getRequestURI());
		return ResponseEntity.status(code.value()).body(error);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClientException (AmazonClientException e, HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
												HttpStatus.BAD_REQUEST.value(), 
												"Erro Amazon Client", 
												e.getLocalizedMessage(),
												request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3Exception (AmazonS3Exception e, HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), 
												HttpStatus.BAD_REQUEST.value(), 
												"Erro Amazon S3", 
												e.getLocalizedMessage(),
												request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
